package ch11.exam03;

public class Member {
    private String id;
    private String name;

    public Member(String id,String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString() {
        return id + " : " + name;
    }

}
```
package ch11.exam03;

public class MemberExample {
    public static void main(String[] args) {
        Member member = new Member("blue", "이파란");
        System.out.println(member);
    }

}
