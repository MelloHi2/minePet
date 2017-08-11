package luckyfish.programs.minepet.crash.v1_0R0;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

/**
 * 欺骗java的玩意
 * 这些东西也真是麻烦死了。。。
 */
public class WriterCheater extends Writer {
	private LinkedList<Integer> ints = new LinkedList<>();

	@Override
	public void write(int b) throws IOException {
		ints.add(b);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		if (off >= 0 && off <= cbuf.length && len >= 0 && off + len <= cbuf.length && off + len >= 0) {
			if (len != 0) {
				for (int i = off; i < len;i ++) {
					ints.add((int) cbuf[i]);
				}
			}
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void flush() throws IOException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 只是清理一下链表即可～
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		ints.clear();
	}

	@Override
	public String toString() {
		char[] bytes = new char[ints.size()];
		for (int i = 0;i < ints.size(); i ++) {
			bytes[i] = (char)((int)ints.get(i));
		}
		return String.copyValueOf(bytes);
	}
}
