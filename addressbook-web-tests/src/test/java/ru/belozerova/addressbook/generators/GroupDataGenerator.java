package ru.belozerova.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.thoughtworks.xstream.XStream;
import ru.belozerova.addressbook.model.GroupData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

    @Parameter(names = "-c", description = "Group count")
    private int count;

    @Parameter(names = "-f", description = "Target file")
    private String file;

    @Parameter(names = "-d", description = "Data format")
    private String format;

    public static void main (String[] args) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try{
            jCommander.parse(args);
        } catch (ParameterException ex) {
        jCommander.usage();//текст, который выводит инфо о доступных опциях
            return;
        }
        //кол-во групп и путь к файлу
        generator.run();
    }

    private void run() throws IOException {
        List<GroupData> groups = generateGroups(count);
        if (format.equals("csv")){
            saveAsCsv(groups, new File(file));
        }
        else if (format.equals("xml")){
            saveAsXml(groups, new File(file));
        }
        else {
            System.out.println("Unrecognized format "+ format);
        }
    }

    private void saveAsXml(List<GroupData> groups, File file) throws IOException {
        XStream xstream = new XStream();
//        xstream.alias("group", GroupData.class);
        xstream.processAnnotations(GroupData.class);
        String xml = xstream.toXML(groups);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }

    private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        Writer writer = new FileWriter(file);
        for (GroupData group : groups){
            writer.write(String.format("%s;%s;%s\n",group.getName(),group.getHeader(), group.getFooter()));
        }
        writer.close();
    }

    private List<GroupData> generateGroups(int count) {
        List<GroupData> groups = new ArrayList<GroupData>();
        for (int i = 0; i< count; i++) {
            groups.add(new GroupData().withName(String.format("test %s", i))
                    .withHeader(String.format("header \n %s", i)).withFooter(String.format("footer %s", i)));
        }
        return groups;
    }
}
