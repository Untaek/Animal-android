package io.untaek.animal.Hash;

public class HashTagListItem {

        private String tags;
        private int counts;

        public String getTag() { return tags; }

        public HashTagListItem(String tags, int counts){
            this.tags = tags;
            this.counts = counts;
        }

}
