package jrd.graduationproject.shoppingplatform.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;

import jrd.graduationproject.shoppingplatform.pojo.vo.JsonConverter;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

public class ReturnJSonFilter implements Filter {

	public ReturnJSonFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 代理（response对象）
		ReturnJSonResponse resp = new ReturnJSonResponse((HttpServletResponse) response);
		chain.doFilter(request, resp);
		// 处理返回值为null的转化为""串
		// 获取字符串
		HttpServletResponse httpRsep = (HttpServletResponse) response;
		byte[] bytes = resp.getByteOut().toByteArray();
		String returnStr = new String(bytes, resp.getCharacterEncoding());
		// 转化格式
		Object json = null;
		try {
			json = JSONObject.parseObject(returnStr);
		} catch (Exception e) {
			try {
				json = JSONArray.parseArray(returnStr);
			} catch (Exception e1) {
				json = returnStr;
			}
		}
		// 返回值过滤（非null）
		if (!(json instanceof String))
			returnStr = JSONObject.toJSONString(json, new ValueFilter() {
				@Override
				public Object process(Object object, String name, Object value) {

					if (value == null || "null".equals(value))
						return "";
					if (name != null && name.toLowerCase().contains("date")) {
						String jsondate = "{\"date\":\"" + value.toString() + "\"}";
						return GlobalUtil.toJsonObject(JsonConverter.class, jsondate).getDate();
					}
					return value;
				}
			});

		// 写入界面
		bytes = returnStr.getBytes(resp.getCharacterEncoding());
		httpRsep.getOutputStream().write(bytes);
		httpRsep.getOutputStream().close();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	class ReturnJSonResponse extends HttpServletResponseWrapper {
		private ByteArrayOutputStream byteOut;
		private ServletOutputStream out;
		private PrintWriter pw;

		public ReturnJSonResponse(HttpServletResponse response) throws IOException {
			super(response);
			byteOut = new ByteArrayOutputStream();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			if (out == null)
				out = new ReturnOutputStream(super.getOutputStream(), byteOut);
			return out;
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if (pw == null)
				pw = new PrintWriter(new OutputStreamWriter(byteOut, getCharacterEncoding()));
			return pw;
		}

		public ByteArrayOutputStream getByteOut() {
			return byteOut;
		}

		class ReturnOutputStream extends ServletOutputStream {
			private ServletOutputStream out;
			private ByteArrayOutputStream byteOut;

			public ReturnOutputStream(ServletOutputStream out, ByteArrayOutputStream byteOut) {
				this.out = out;
				this.byteOut = byteOut;
			}

			@Override
			public boolean isReady() {
				return out.isReady();
			}

			@Override
			public void setWriteListener(WriteListener arg0) {
				out.setWriteListener(arg0);
			}

			@Override
			public void write(int b) throws IOException {
				byteOut.write(b);
			}
		}
	}

}
