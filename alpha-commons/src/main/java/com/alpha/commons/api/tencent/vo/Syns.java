/**
  * Copyright 2017 bejson.com 
  */
package com.alpha.commons.api.tencent.vo;
import java.util.List;

/**
 * Auto-generated: 2017-09-18 18:35:17
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Syns {

    private Word_ori word_ori;
    private List<Word_syns> word_syns;
    public void setWord_ori(Word_ori word_ori) {
         this.word_ori = word_ori;
     }
     public Word_ori getWord_ori() {
         return word_ori;
     }

    public void setWord_syns(List<Word_syns> word_syns) {
         this.word_syns = word_syns;
     }
     public List<Word_syns> getWord_syns() {
         return word_syns;
     }

}