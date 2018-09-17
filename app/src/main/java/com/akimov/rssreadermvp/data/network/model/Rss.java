package com.akimov.rssreadermvp.data.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by lex on 9/7/18.
 */
@Root(name = "rss", strict = false)
public class Rss /*implements Serializable*/ {

  public Rss() {
  }
  public Rss(Channel channel) {
    this.channel = channel;
  }

  @Element(name="channel")
  public Channel channel;
}
