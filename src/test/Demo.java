package test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 练习题，代码实现
 * @author Peng.Zezhou
 *
 */
public class Demo {

	// 日志记录对象
	private static final Logger log = Logger.getLogger(Demo.class.getName());

	// 16进制字符数组
	private static char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	
	/**
	 * 将文件内容转换成byte数组返回,如果文件不存在、读入错误、文件大小超过2G则返回null
	 * 
	 * <pre>
	 * byte[] b = file2buf(new File(&quot;D:\tmp.txt&quot;));
	 * </pre>
	 * 
	 * @param fobj
	 *            文件对象 File
	 * @return byte数组
	 * @throws IOException
	 *             文件输入输出流异常
	 * 
	 */
	public byte[] file2buf(File fobj) throws IOException {
		if (fobj == null || fobj.isDirectory() || !fobj.exists()
				|| fobj.length() > (1024 * 1024 * 1024 * 2 - 1)) {
			log.log(Level.SEVERE, "输入参数为null、不是文件、文件不存在或者文件大小超过2G");
			return null;
		}

		log.log(Level.INFO, "文件开始转换为字节数组...");
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		byte[] bytes = null;

		try {
			fis = new FileInputStream(fobj);
			bos = new ByteArrayOutputStream((int) fobj.length());
			byte[] b = new byte[4096];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			bytes = bos.toByteArray();
		} catch (Exception e) {
			log.log(Level.SEVERE, "文件转换为字节数组出现异常");
		} finally {
			try {
				fis.close();
				bos.close();
			} catch (IOException e) {
				log.log(Level.INFO, "流关闭出现异常");
			}
		}

		log.log(Level.INFO, "文件开始转换为字节数组结束");
		return bytes;
	}

	/**
	 * 将一个整数转换为16进制的字符串，如果输入为负数则返回null
	 * 
	 * <pre>
	 * String s = intToHex(17);
	 * </pre>
	 * 
	 * @param n
	 *            一个整数
	 * @return String 16进制的字符串
	 */
	public String intToHex(int n) {
		StringBuilder str = new StringBuilder(11);
		str.append("0X00000000H");

		if (n < 0) {
			log.log(Level.INFO, "不接收转换负数" + n);
			return null;
		}
		int num = Math.abs(n);
		for (int i = 9; i >= 2; i--) {
			if (num != 0) {
				str.setCharAt(i, HEX_CHARS[num & 15]);
				num >>>= 4;
			} else {
				break;
			} 
		}
		log.log(Level.INFO, n + "转16进制" + str + "正常结束");
		return str.toString();
	}

	/**
	 * 返回树tree的第n层的所有节点值，并且输出顺序为从左到右
	 * 
	 * <pre>
	 * List&lt;TNode&gt; nl = TreeLevel(tree, n);
	 * </pre>
	 * 
	 * @param tree
	 *            树顶层节点
	 * @param n
	 *            指定层数
	 * @return 节点集合
	 */
	public List<TNode> treeLevel(TNode tree, int n) {

		// 二叉树层节点保存
		List<TNode> nodeList = new LinkedList<TNode>();
		nodeList.clear();
		findNode(tree, n, nodeList);
		return nodeList;
	}

	/**
	 * 找到二叉树指定层节点，并存储在list中
	 * 
	 * <pre>
	 * findNode(node, n,nodeList);
	 * </pre>
	 * 
	 * @param tree
	 *            二叉树树根
	 * @param n
	 *            指定层
	 * @param nodeList
	 *            存储节点的集合
	 * @return 二叉树不存在或左右节点不存在返回0，否则记录找到的节点数到list，返回其个数
	 */
	private int findNode(TNode tree, int n, List<TNode> nodeList) {
		if (tree == null || n <= 0)
			return 0;
		if (1 == n) {
			nodeList.add(tree);
			return 1;
		}
		return findNode(tree.getLeft(), n - 1, nodeList)
				+ findNode(tree.getRight(), n - 1, nodeList);
	}
}