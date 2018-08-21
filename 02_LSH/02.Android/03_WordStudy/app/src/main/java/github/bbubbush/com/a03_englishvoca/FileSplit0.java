package github.bbubbush.com.a03_englishvoca;

public class FileSplit0 {
	public static String questionNum[][] = new String[100][10];

	public FileSplit0(String str) {
		String tmp[] = str.split("\n");
		String s;

		for (int i = 0; i < tmp.length; i++) {
	
			s = tmp[i];
			String tmp2[] = s.split(":");
			
			for(int j = 0; j <10  ; j++){
				tmp2[j]=tmp2[j].trim();
			questionNum[i][j]=tmp2[j];
					
			}
		}
	}

}
