package ru.belozerova.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.belozerova.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

        @Parameter(names = "-c", description = "Contact count")
        private int count;

        @Parameter(names = "-f", description = "Target file")
        private String file;

        @Parameter(names = "-d", description = "Data format")
        private String format;

        public static void main (String[] args) throws IOException {
            ContactDataGenerator generator = new ContactDataGenerator();
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
            List<ContactData> contacts = generateContacts(count);
            if (format.equals("csv")){
                saveAsCsv(contacts, new File(file));
            }
            else if (format.equals("xml")){
                saveAsXml(contacts, new File(file));
            }
            else if (format.equals("json")){
                saveAsJson(contacts, new File(file));
            } else
                System.out.println("Unrecognized format "+ format);
        }


        private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
            XStream xstream = new XStream();
            xstream.processAnnotations(ContactData.class);
            String xml = xstream.toXML(contacts);
            Writer writer = new FileWriter(file);
            writer.write(xml);
            writer.close();
        }
        private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
           //сериализуем объект в файл
            Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(contacts); //превращаем объект в строку.
            //System.out.println("строка для записи: " + json);
            Writer writer = new FileWriter(file);
            writer.write(json); //строку пишем в файл
            writer.close();
        }

        private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
            System.out.println(new File(".").getAbsolutePath());
            Writer writer = new FileWriter(file);
            for (ContactData contact : contacts){
                writer.write(String.format("%s;%s;%s;%s;%s;\n",contact.getFirstName(),contact.getLastName(),
                        contact.getAddress(), contact.getEmail(), contact.getMobilePhone()));
            }
            writer.close();
        }

        private List<ContactData> generateContacts(int count) {
            List<ContactData> contacts = new ArrayList<ContactData>();
            for (int i = 0; i< count; i++) {
                contacts.add(new ContactData().withFirstName(String.format("Alfa %s", i))
                        .withLastName(String.format("Beta %s", i)).withAddress(String.format("Earth, house \n %s", i)).
                                withEmail("alfa@beta.com").withMobilePhone("+71234567890"));
            }
            return contacts;
        }
    }

