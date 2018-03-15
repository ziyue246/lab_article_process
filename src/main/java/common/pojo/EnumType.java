package common.pojo;

/**
 * Created by ziyue on 2018/3/14.
 */
public class EnumType {

    public enum AuthorType {
        ORDINARY("普通作者",0),REPRINT("通讯作者",1);


        private String name;
        private int index;

        AuthorType(String name, int index) {
            this.name = name;
            this.index = index;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }





}
