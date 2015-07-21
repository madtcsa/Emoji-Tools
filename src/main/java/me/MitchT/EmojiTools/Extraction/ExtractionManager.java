package me.MitchT.EmojiTools.Extraction;

import me.MitchT.EmojiTools.EmojiTools;
import me.MitchT.EmojiTools.Extraction.Extractors.AppleExtractionThread;
import me.MitchT.EmojiTools.Extraction.Extractors.ExtractionThread;
import me.MitchT.EmojiTools.Extraction.Extractors.GoogleExtractionThread;
import me.MitchT.EmojiTools.Extraction.Extractors.StandardExtractionThread;
import me.MitchT.EmojiTools.GUI.EmojiToolsGUI;
import me.MitchT.EmojiTools.GUI.ExtractionDialog;
import me.MitchT.EmojiTools.OperationManager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

public class ExtractionManager extends OperationManager {

    private final EmojiToolsGUI gui;

    private ExtractionThread extractionThread;

    public ExtractionManager(File font, File extractionDirectory, EmojiToolsGUI gui, ExtractionDialog extractionDialog) {
        this.gui = gui;

        //Determine which Extraction Method to use
        try {
            RandomAccessFile inputStream = new RandomAccessFile(font, "r");

            byte[] b = new byte[4];
            inputStream.readFully(b);

            if (!ExtractionUtilites.compareBytes(b, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00)) {
                showMessageDialog("Selected Font is not a valid True Type Font! (*.ttf)");
                inputStream.close();
                return;
            }

            b = new byte[2];
            inputStream.readFully(b);

            short numTables = ExtractionUtilites.getShortFromBytes(b);
            List<String> tableNames = Arrays.asList(new String[numTables]);
            List<Integer> tableOffsets = Arrays.asList(new Integer[numTables]);
            List<Integer> tableLengths = Arrays.asList(new Integer[numTables]);

            inputStream.seek(12);
            b = new byte[4];
            for (int i = 0; i < numTables; i++) {
                inputStream.readFully(b);
                tableNames.set(i, ExtractionUtilites.getStringFromBytes(b));
                inputStream.skipBytes(4);
                inputStream.readFully(b);
                tableOffsets.set(i, ExtractionUtilites.getIntFromBytes(b));
                inputStream.readFully(b);
                tableLengths.set(i, ExtractionUtilites.getIntFromBytes(b));
            }

            if (tableNames.contains("sbix"))
                extractionThread = new AppleExtractionThread(font, extractionDirectory, tableNames, tableOffsets, tableLengths, this, extractionDialog);
            else if (tableNames.contains("CBLC") && tableNames.contains("CBDT"))
                extractionThread = new GoogleExtractionThread(font, extractionDirectory, tableNames, tableOffsets, tableLengths, this, extractionDialog);
            else
                extractionThread = new StandardExtractionThread(font, extractionDirectory, this, extractionDialog);

            inputStream.close();
        } catch (IOException e) {
            EmojiTools.submitError(Thread.currentThread(), e);
        }
    }

    public void showMessageDialog(String message) {
        this.gui.showMessageDialog(message);
    }

    @Override
    public void start() {
        extractionThread.start();
    }

    @Override
    public void stop() {
        if (extractionThread != null && extractionThread.isAlive()) {
            extractionThread.endExtraction();
        }
    }
}
