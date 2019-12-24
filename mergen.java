import java.io.*;
public class mergen{    
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
		int LEN=0,rlen=0;
		String str="",outstr="";
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
			if (str.charAt(0)=='>')
			{
				cnt=cnt+1;
				if (cnt>lcnt)
				{
					if (outstr.length()>0)
					{
						ocnt++;
						bw.write(">"+ocnt+"\n");
						while (outstr.length()>0)
                                                {
                                                        if (outstr.length()>LEN) rlen=LEN;
                                                        else rlen=outstr.length();
                                                        bw.write(outstr.substring(0,rlen)+"\n");
                                                        outstr=outstr.substring(rlen);
                                                }
						outstr="";
						cnt=0;
					}	
				}
				else if (outstr.length()>0) outstr=outstr+NN;
				continue;
			}
			if (LEN==0) LEN=str.length();
			outstr=outstr+str;
		}
		if (outstr.length()>0)
		{
			ocnt++;
			bw.write(">"+ocnt+"\n");
			while (outstr.length()>0)
                        {
                              if (outstr.length()>LEN) rlen=LEN;
                              else rlen=outstr.length();
                              bw.write(outstr.substring(0,rlen)+"\n");
                              outstr=outstr.substring(rlen);
                        }
		}	
		bw.flush();
		bw.close();
		br.close();
	}
}
