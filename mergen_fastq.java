import java.io.*;
public class mergen_fastq{    
	public static void main(String args[]) throws Exception
	{  
		if (args.length<4)
		{
			System.out.println("java mergen input_file how_many_line how_many_N  output_file");
			return;
		}
		String inp_file=args[0];
		String out_file=args[3];
		String NN="";
		String str="",outstr="";
		String idstr="",idoutstr="";
		String qcstr="",qcoutstr="";
		int LEN=0,rlen=0;
		int mode=0;//0:unknown mode 1:id mode 2:str mode 3:qc mode
		int lcnt=Integer.parseInt(args[1]);
		int ncnt=Integer.parseInt(args[2]);
		int cnt,ocnt=0;
		for (int i=0;i<ncnt;i=i+1)
			NN=NN+"N";
		BufferedReader br= new BufferedReader(new FileReader(inp_file));
		BufferedWriter bw= new BufferedWriter(new FileWriter(out_file));
		cnt=0;
		outstr="";
		while (br.ready())
		{
			str=br.readLine().trim();
			if (str.length()<=0) continue;
			if (str.charAt(0)=='@')
			{
				mode=1;
				cnt=cnt+1;
				if (cnt>lcnt)
				{
					if (outstr.length()>0)
					{
						ocnt++;
						bw.write("@"+idoutstr+"\n");
						while (outstr.length()>0)
						{
							if (outstr.length()>LEN) rlen=LEN;
							else rlen=outstr.length();
							bw.write(outstr.substring(0,rlen)+"\n");
							outstr=outstr.substring(rlen);
						}

						bw.write("+\n"+qcoutstr+"\n");
						outstr="";
						idoutstr=str.substring(1);
						qcoutstr="";
						cnt=0;
					}	
				}
				else {
					if (outstr.length()>0) outstr=outstr+NN;
					idoutstr=idoutstr+str.substring(1);
				}
				mode=2;
				continue;
			}
			if (str.charAt(0)=='+') {
				mode=3;
				continue;
			}
		 	if (mode==2)	
			{
				if (LEN==0) LEN=str.length();
				outstr=outstr+str;
				//System.out.println("LEN="+LEN);
			}
			else if (mode==3){
				qcoutstr=qcoutstr+str;
				mode=0;
			}
		}
		if (outstr.length()>0)
		{
			ocnt++;
			bw.write("@"+idoutstr+"\n");
                        while (outstr.length()>0)
                        {
		  	    if (outstr.length()>LEN) rlen=LEN;
                            	else rlen=outstr.length();

                            bw.write(outstr.substring(0,rlen)+"\n");
                            outstr=outstr.substring(rlen);
                        }
                        bw.write("+\n"+qcoutstr+"\n");

		}	
		bw.flush();
		bw.close();
		br.close();
	}
}
