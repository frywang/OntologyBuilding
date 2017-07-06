package merge;

/*
 * 该文件把文件夹里的所有文件合并成一个
 * */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MergeDBpediaTXT {

	// 列出所有文件名
	public static List<String> readFile(String filepath) {
		// 创建file对象
		File file = new File(filepath);
		// truefilist是确定为文件的路径
		List<String> truefilelist = new ArrayList<String>();
		List<String> choosedfilelist = new ArrayList<String>();
		try {
			if (!file.isDirectory()) {
				System.out.println("这不是一个文件夹");
				System.out.println("path=" + file.getPath());
				System.out.println("absolutepath=" + file.getAbsolutePath());
				System.out.println("name=" + file.getName());
			} else if (file.isDirectory()) {
				// filelist有可能包含folder
				String[] filelist = file.list();
				for (String f : filelist) {
					File readfile = new File(filepath + "//" + f);
					if (!readfile.isDirectory()) {
						// System.out.println("absolutepath="+readfile.getAbsolutePath());
						truefilelist.add(readfile.getAbsolutePath());
					} else if (readfile.isDirectory()) {
						truefilelist.addAll(readFile(filepath + "//" + f));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("readFile()   Exception:" + e.getMessage());
			e.printStackTrace();
		}

		for (String list : truefilelist) {
			if ((list.contains("值")) && (list.contains("同义"))) {
				choosedfilelist.add(list);
			}
		}

		System.out.println(choosedfilelist);
		return choosedfilelist;

	}

	// 将所有的txt文件写入同一个文件
	public static void txt2String(List<String> list) throws IOException {
		List<String> line1 = new ArrayList<String>();
		for (String f : list) {
			File filename = new File(f);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = "";
			// line = bufferedReader.readLine();
			// System.out.println("####");
			// 将绝对路径重新转变成相对路径
			f = f.substring(f.lastIndexOf("/") + 1, f.lastIndexOf(".")).replaceAll("（.*）", "");
			// System.out.println(f);
			while (line != null) {
				line = bufferedReader.readLine();
				// if(line.contains("INFO:")){
				// String newline = line.replaceAll("\r|\n", "afdafaf");
				// break;
				// }
				System.out.println(line);
				line1.add(f + "####" + line);
			}

		}
		try {
			File writename = new File("datas/历史人物merge_corpus.txt");
			FileWriter fw = new FileWriter(writename);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < line1.size(); i++) {
				// System.out.println(line1.get(i));
				bw.write(line1.get(i) + "\n");
				;
				; // \r\n即为换行
				bw.flush(); // 把缓存区内容压入文件
			}
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// 需要处理的文件夹
		// String filepath = "/home/fry/workspace/DBpedia/datas/结果/";
		String filepath = "/home/fry/workspace/OntologyBuilding/datas/人类";

		// System.out.println(readFile(filepath));
		// 根据文件夹生成真正的文件
		List<String> filepathChildren = readFile(filepath);
		System.out.println(filepathChildren.size());
		System.out.println(filepathChildren.toString());
		txt2String(filepathChildren);

	}
}