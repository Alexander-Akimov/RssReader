package com.akimov.rssreadermvp.data;

import com.akimov.rssreadermvp.business.models.RssPost;
import com.akimov.rssreadermvp.data.network.model.Item;

/**
 * Created by lex on 9/10/18.
 */

public class Mapper {
  static RssPost toRssPost(Item item) {
    return new RssPost(item.getTitle(), item.getDescription(), item.getLink(), 0);
  }
}