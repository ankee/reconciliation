package com.sample.problem1;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.sample.problem1.model.Item;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by aagarwal1 on 2/28/2015.
 */
public class ItemReader implements Iterable<Item>, Closeable {

    private XMLEventReader reader;
    private boolean isClosed;
    private Item nextItem = null;

    private ItemReader(File file) {

    }

    /**
     * Reads all Item elements in the xml.
     * Returns an Immutable Set with all Items in the xml report
     *
     * @param dataFile xml file with Item details
     * @return Set of all item data present in the xml input
     */
    public static Set<Item> readItemData(String dataFile) {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = null;
        ImmutableSet.Builder<Item> items = new ImmutableSet.Builder<Item>();

        Item.ItemBuilder itemBuilder = null;
        String elementData = null;
        try {
            reader = factory.createXMLEventReader(new FileReader(dataFile));


            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement element = event.asStartElement();
                    if ("item".equals(element.getName())) {
                        itemBuilder = constructItemBuilder(element);
                    }
                } else if (event.isCharacters()) {
                    Characters characters = event.asCharacters();
                    elementData = characters.getData();
                } else if (event.isEndElement()) {
                    EndElement element = event.asEndElement();
                    if ("name".equals(element.getName().getLocalPart())) {
                        Preconditions.checkNotNull(itemBuilder, "Item Builder should be initialized in start element");
                        itemBuilder.setName(elementData);
                    } else if ("data".equals(element.getName().getLocalPart())) {
                        Preconditions.checkNotNull(itemBuilder, "Item Builder should be initialized in start element");
                        itemBuilder.setData(elementData);
                    } else if ("item".equals(element.getName().getLocalPart())) {
                        Preconditions.checkNotNull(itemBuilder, "Item Builder should be initialized in start element");
                        items.add(itemBuilder.build());
                    }

                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(reader);
        }
        return items.build();
    }

    private static Item.ItemBuilder constructItemBuilder(StartElement element) {
        Attribute id = element.getAttributeByName(new QName("id"));
        Item.ItemBuilder itemBuilder = Item.getItemBuilder();
        Integer itemId = Integer.valueOf(Integer.parseInt(id.getValue()));
        itemBuilder.setId(itemId);
        return itemBuilder;
    }


    private static void close(XMLEventReader reader) {
        try {
            if (reader != null)
                reader.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public static ItemReader openItemReader(File file) {
        ItemReader itemReader = new ItemReader(file);
        try {
            itemReader.reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(file));

        } catch (XMLStreamException | FileNotFoundException e) {
            throw new RuntimeException(e); //Could be replaced with a custom checked exception

        }
        return itemReader;

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                if (isClosed) throw new IllegalStateException("ItemReader has already been closed!");
                return hasNextInternal();
            }

            @Override
            public Item next() {
                if (isClosed) throw new IllegalStateException("ItemReader has already been closed!");
                try {
                    return readNextInternal();
                } catch (XMLStreamException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove is not supported!");
            }
        };
    }

    private Item readNextInternal() throws XMLStreamException {
        Item.ItemBuilder itemBuilder = null;
        String elementData = null;

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if ("item".equals(element.getName().getLocalPart())) {
                    itemBuilder = constructItemBuilder(element);
                }
            } else if (event.isCharacters()) {
                Characters characters = event.asCharacters();
                elementData = characters.getData();
            } else if (event.isEndElement()) {
                EndElement element = event.asEndElement();
                if ("name".equals(element.getName().getLocalPart())) {
                    Preconditions.checkNotNull(itemBuilder, "Item Builder should be initialized in start element");
                    itemBuilder.setName(elementData);
                } else if ("data".equals(element.getName().getLocalPart())) {
                    Preconditions.checkNotNull(itemBuilder, "Item Builder should be initialized in start element");
                    itemBuilder.setData(elementData);
                } else if ("item".equals(element.getName().getLocalPart())) {
                    Preconditions.checkNotNull(itemBuilder, "Item Builder should be initialized in start element");
                    return itemBuilder.build();
                }
            }
        }
        throw new IllegalStateException("next should be invoked only when hasNext returns true");
    }

    private boolean hasNextInternal() {
        if (reader.hasNext()) {
            try {
                XMLEvent event = reader.peek();
                if(event.isCharacters() && event.asCharacters().isWhiteSpace()){
                    reader.nextEvent(); //ignore
                    return hasNextInternal();
                }
                if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("items")) {
                    return false;
                }
                return true;
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
/*
    private boolean parseNextElement() {
        Item.ItemBuilder itemBuilder = null;
        String elementData = "";
        try {

            while (reader.hasNext()) {
                int type = reader.getEventType();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        if ("item".equals(reader.getLocalName())) {
                            itemBuilder = constructItemBuilder(reader);
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        elementData = reader.getText().trim();
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        if ("name".equals(reader.getLocalName())) {
                            Preconditions.checkNotNull(itemBuilder, "Item Builder should be initialized in start \"item\" element");
                            itemBuilder.setName(elementData);
                        } else if ("data".equals(reader.getLocalName())) {
                            Preconditions.checkNotNull(itemBuilder, "Item Builder should be initialized in start \"item\" element");
                            itemBuilder.setData(elementData);
                        } else if ("item".equals(reader.getLocalName())) {
                            Preconditions.checkNotNull(itemBuilder, "Item Builder should be initialized in start \"item\" element");
                            nextItem = itemBuilder.build();
                            return true;
                        }
                        break;
                    default://do nothing
                        break;
                }

            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }*/
}
