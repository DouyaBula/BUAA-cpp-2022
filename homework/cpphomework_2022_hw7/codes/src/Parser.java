import java.util.ArrayList;
import java.util.HashMap;

// 语法分析器
public class Parser {
    private String tweet;
    private Block block;
    private HashMap<String, Value> content = new HashMap<>();
    //词法分析器
    private Lexer lexer;

    public Block parse() {
        lexer = new Lexer(tweet);
        lexer.next();
        return parseObject();
    }

    // parseObject专门解析并返回json对象
    public Block parseObject() {
        // 1、当前 Lexer 读到的是左大括号
        Block temp = new Block(new HashMap<>());
        // 2、循环读取 Lexer 的下一个词法
        //        若是右大括号，则停止循环
        //         若不是，则调用parseAttribute
        while (true) {
            lexer.next();
            if (lexer.getToken().getType().equals("}")) {
                break;
            } else if (lexer.getToken().getType().equals(",")) {
                continue;
            } else {
                temp.addContent(lexer.getToken().getContent(), parseAttribute());
            }
        }
        // 3、返回一个json对象
        return temp;
    }

    //parseAttribute专门解析并返回一个属性对象
    public Value parseAttribute() {
        // 1、当前 Lexer 读到的是属性的名称, 读取下一个词法
        Value temp;
        lexer.next(); //冒号
        lexer.next();
        // 2、分类讨论 Lexer 之后读取的内容
        //        若是左大括号，则调用parseObject读取json对象作为属性
        //        若是左中括号，则调用parseArray读取数组对象作为属性
        //        若是普通的字符串，则创建普通的字符串属性对象
        //        若是数字，则创建数字属性对象
        //        ...
        if (lexer.getToken().getType().equals("{")) {
            temp = parseObject();
        } else if (lexer.getToken().getType().equals("[")) {
            temp = parseArray();
        } else {
            temp = new StringForm(lexer.getToken().getType(), lexer.getToken().getContent());
        }
        // 3、返回一个属性对象
        return temp;
    }

    // parseArray专门解析并返回数组对象
    public Array parseArray() {
        // 1、当前 Lexer 读到的是左方括号
        Array temp = new Array(new ArrayList<>());
        // 2、循环读取 Lexer 的下一个词法
        //        若是右方括号，则停止循环
        //        若不是，则一定是json对象或逗号
        //        小tip：java中可以用Object表示任意类型
        while (true) {
            lexer.next();
            if (lexer.getToken().getType().equals("]")) {
                break;
            } else if (lexer.getToken().getType().equals("{")) {
                temp.addContent(parseObject());
            }
        }
        // 3、返回一个数组对象
        return temp;
    }

    public Parser(String tweet) {
        this.tweet = tweet;
    }
}