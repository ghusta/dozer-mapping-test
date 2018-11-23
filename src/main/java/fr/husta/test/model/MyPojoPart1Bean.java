package fr.husta.test.model;

public class MyPojoPart1Bean {

    private String name;

    // nested bean
    private MyNestedPojoBean myNestedPojoBean;

    public MyPojoPart1Bean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyNestedPojoBean getMyNestedPojoBean() {
        return myNestedPojoBean;
    }

    public void setMyNestedPojoBean(MyNestedPojoBean myNestedPojoBean) {
        this.myNestedPojoBean = myNestedPojoBean;
    }
}
