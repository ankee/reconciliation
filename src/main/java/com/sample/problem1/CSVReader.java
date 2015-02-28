package com.sample.problem1;

import com.google.common.base.Splitter;
import com.sample.problem1.model.ClearanceData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by aagarwal1 on 2/28/2015.
 */
public class CSVReader {


    public static Set<ClearanceData> readAll(File file)  {
        BufferedReader reader = null;
        Set<ClearanceData> dataSet = new HashSet<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null){
                List<String> rowData = Splitter.on(",").splitToList(line);
                Integer id = Integer.parseInt(rowData.get(0));
                dataSet.add( new ClearanceData(id, rowData.get(1).trim(), Boolean.parseBoolean(rowData.get(2).trim())));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    return dataSet;
    }
}
