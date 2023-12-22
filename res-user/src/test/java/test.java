import org.springframework.util.DigestUtils;

public class test {
    public static void main(String[] args) {
        String md5Pass = DigestUtils.md5DigestAsHex("a".getBytes());
        System.out.println(md5Pass);
    }
}
