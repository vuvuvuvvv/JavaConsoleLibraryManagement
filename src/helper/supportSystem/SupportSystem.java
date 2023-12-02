package helper.supportSystem;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupportSystem {
	
	public ArrayList<String> role = new ArrayList<>();
	
	public SupportSystem() {
		this.role.add("admin");
		this.role.add("seeller");
		this.role.add("customer");
	}
	
	public static void clearConsole() {
        String os = System.getProperty("os.name").toLowerCase();
        boolean isWindows = os.contains("win");
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Clear the terminal based on the operating system
        if (isWindows) {
            clearWindowsTerminal();
        } else {
            clearUnixLikeTerminal();
        }
	}
	
	private static void clearWindowsTerminal() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void clearUnixLikeTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
	
	
    public static String toSlug(String input) {
        // Replace Vietnamese characters with their Latin equivalents
        String converted = convertVietnameseToLatin(input.trim());

        // Replace non-alphanumeric characters with hyphens, remove remaining non-ASCII characters, and convert to lowercase
        String slug = converted.replaceAll("[^\\p{ASCII}a-zA-Z0-9\\s-]", "")
                              .replaceAll("\\s+", "-")
                              .toLowerCase();
        return slug;
    }
    
    public static String convertVietnameseToLatin(String input) {
        String[][] replacements = {
	   	    {"a","á","à","ã","ạ","a","â","ấ","ầ","ẫ","ậ","â","ă","ắ","ằ","ẫ","ặ","ẳ"},
	    	{"o","ó","ò","õ","ọ","o","ô","ố","ồ","ỗ","ộ","ô","ơ","ớ","ờ","ỡ","ợ","ở"},
	    	{"A","Á","À","Ã","Ạ","A","Â","Ấ","Ầ","Ẫ","Ậ","Â","Ă","Ắ","Ằ","Ẫ","Ặ","Ă"},
	    	{"O","Ó","Ò","Õ","Ọ","Ỏ","Ô","Ố","Ồ","Ỗ","Ộ","Ổ","Ơ","Ớ","Ờ","Ỡ","Ợ","Ở"},
	    	{"E","É","È","Ẽ","Ẹ","Ẻ","Ê","Ế","Ề","Ễ","Ệ","Ể"},
	    	{"U","Ú","Ù","Ũ","Ụ","Ủ","Ư","Ữ","Ứ","Ừ","Ự","Ử"},
	    	{"u","ú","ù","ũ","ụ","ủ","ư","ữ","ứ","ừ","ự","ử"},
	    	{"e","é","è","ẽ","ẹ","e","ê","ế","ề","ễ","ệ","ể"},
	    	{"i","í","ì","ĩ","ị","ỉ"},
	    	{"y","ý","ỳ","ỹ","ỵ","ỷ"},
	    	{"I","Í","Ì","Ĩ","Ị","Ỉ"},
	    	{"Y","Ý","Ỳ","Ỹ","Ỵ","Ỷ"},
	    	{"d","đ"},
	    	{"D","Đ"}
        };

        // Perform replacements
        for (String[] replacement : replacements) {
            for (String variant : replacement) {
            	if(variant == replacement[0]) {
            		continue;
            	}
            	if(input.indexOf(variant) != -1) {
                    input = input.replace(variant, replacement[0]);
            	}
            }
        }
        return input;
    }
    
    public static String formatMenuDataCol(String name, int stt) {
    	int space = 20;
    	name = stt +". "+name;
		String[] wordsArray = name.split(" ");
		String text_rows = "";
		if (toSlug(name).length() > space) {
			ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(wordsArray));
			for (int i = 0; i < wordsList.size(); i++) {
				String word = wordsList.get(i);
				// Tru di 3 dau "." va 1 " "
				if (toSlug(text_rows).length() + toSlug(word).length() > space -3 -1) {
					int count_space = space - toSlug(text_rows).length();
					if (count_space > 2) {
						text_rows += " ...";
						for (int k = 0; k < count_space -3 -1; k++) {
							text_rows += " ";
						}
					} else {
						for (int k = 0; k < count_space; k++) {
							text_rows += " ";
						}
					}
					break;
				}
				text_rows += ((text_rows.length() == 0) ? "" : " ") + word;
			}
		} else {
			text_rows = name;
			int count_space = space - toSlug(text_rows).length();
			if (count_space > 0) {
				for (int k = 0; k < count_space; k++) {
					text_rows += " ";
				}
			}
		}
    	return text_rows;
    }
    
    private static boolean isPhoneNumberValid(String phoneNumber) {
    	String regex = "^(0[35789])\\d{8}$";
    	
    	Pattern pattern = Pattern.compile(regex);
    	
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    
    private static boolean isPasswordValid(String password) {
    	//It nhat 1 chu cai, 1 so va toi thieu 8 ky tu
    	String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@#$%^&-_]{8,}$";
    	
    	Pattern pattern = Pattern.compile(regex);
    	
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}



