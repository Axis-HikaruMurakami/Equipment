package Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

// 文字化け対策フィルター
@WebFilter("/*")
public class EncodingFilter implements Filter {

    @Override
    public void destroy() {
    	
    }

    //リクエストとレスポンスの文字化け対策を行うメソッド
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // リクエストの文字化け対策
        request.setCharacterEncoding("UTF-8");
        
        // レスポンスの文字化け対策
        response.setCharacterEncoding("UTF-8");

        // リクエスト対象の呼び出し
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    
    }
}