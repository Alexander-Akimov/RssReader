package com.akimov.rssreadermvp.data.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by lex on 9/8/18.
 */
@Root(name = "channel", strict = false)
public class Channel {
  public Channel() {
  }

  public Channel(String title, String description, String link, String link_atom, String language, List<Item> items) {

    this.title = title;
    this.description = description;
    //this.link = link;
    this.language = language;
    this.items = items;
  }

  @Element(name = "title")
  private String title;

  @Element(name = "description")
  private String description;

  //@Path(value = "channel/link")
//  @Element(name = "link")
//  private String link;

  @ElementList(entry = "link", inline = true, required = false)
  private List<String> links;

  @Element(name = "language")
  private String language;

  @ElementList(entry = "item", inline = true)
  private List<Item> items;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

//  public String getLink() {
//    return link;
//  }
//
//  public void setLink(String link) {
//    this.link = link;
//  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}
