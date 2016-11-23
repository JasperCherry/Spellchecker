import java.util.ArrayList;

public class Levenshtein {
	public int count(String word1, String word2){
		int distance=0;
		
		ArrayList<Character> word1p = new ArrayList<Character>();
		ArrayList<Character> word2p = new ArrayList<Character>();
		
		// adding all ascii characters that should matter
		for (int i = 0;i < word1.length(); i++){
		    if(	(int)word1.charAt(i)>=32&&(int)word1.charAt(i)<=127){
		    	word1p.add(word1.charAt(i));
		    }			
		}
		for (int i = 0;i < word2.length(); i++){
		    if(	(int)word2.charAt(i)>=32&&(int)word2.charAt(i)<=127){
		    	word2p.add(word2.charAt(i));
		    }			
		}
		
		if(word1p.size()==0&&word2p.size()!=0){
			return word2p.size();
		}
		if(word1p.size()!=0&&word2p.size()==0){
			return word1p.size();
		}
		if(word1p.size()==0&&word2p.size()==0){
			return 0;
		}
		
		
		int addDist=0;
		int current=0;
		// matrix
		int[][]m = new int[word1p.size()+1][word2p.size()+1];
		
		// filling first row and first column of matrix
		for(int q=0; q<word1p.size()+1; q++){
			m[q][0]=q;
		}
		
		for(int q=0; q<word2p.size()+1; q++){
			m[0][q]=q;
		}
		
		// fill the rest
		for(int q=0; q<word1p.size(); q++){
			for(int qq=0; qq<word2p.size(); qq++){
				if(word1p.get(q)==word2p.get(qq)){
					addDist=0;
				}else{
					addDist=1;
				}
				// counting minimal value for 3 options
				current=Min(m[q+1-1][qq+1]+1,m[q+1][qq+1-1]+1,m[q][qq]+addDist);
				m[q+1][qq+1]=current;
			}
		}
		
		
		// print the array
		/*
		for(int q=0; q<word1p.size()+1; q++){
			for(int qq=0; qq<word2p.size()+1; qq++){
				System.out.print(m[q][qq]);
			}
			System.out.println("");
		}	
		*/
		
		// set the distance
		distance=m[word1p.size()][word2p.size()];
		
		return distance;
	}
	
	// counting minimal value for 3 options
	private int Min(int x, int y, int z){
		int min = x;
		if(min>y){
			min=y;
		}
		if(min>z){
			min=z;
		}
		return min;
	}
	
}
