package com.akimov.rssreadermvp.data.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by lex on 9/7/18.
 */
@Root(name = "item", strict = false)
public class Item {

  public Item() {
  }

  public Item(String title, String description, String link) {
    this.title = title;
    this.description = description;
    this.link = link;
  }

  @Element(name = "title")
  private String title;

  @Element(name = "description")
  private String description;

  //@Path("item/link")
  @Element(name = "link")
  private String link;


  //private String pubDate;

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

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
