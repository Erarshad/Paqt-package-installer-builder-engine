package core;

import paqt.custom_io;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class robotus {


    public static int phase=1;
    String path=  System.getProperty("user.dir")+"\\"+"custom_action\\";


    public   void write() throws  Exception{
        symbol syntax=new symbol();



        syntax.init(); // it will initiate symbol table
        create_env();// it will ready inviroment for us
        FileWriter fstream = new FileWriter (path+"ide.nsi");
        BufferedWriter info = new BufferedWriter(fstream);
        boolean is_desktop_allow=true;
        boolean is_taskbar_allow=false;



        while(true){
            if(phase==1){
                // it import library
                String lib=syntax.lib;
                String line=syntax.syntax.get("import")+" "+"\"" + lib + "\"";
                info.write(line);
                info.newLine();
                phase=2;
            }else if(phase==2){
                // here we deal with def

                for(String gen: syntax.phase2){

                    symbol_param mid=syntax.predefined_func.get(gen);
                    String fun=mid.syntax;
                    int  param=mid.is_have_param;

                        String parameter = syntax.params.get(syntax.predefined_func.get(gen).is_have_param);
                        String line = "";
                        if(param!=0) {
                            line = syntax.syntax.get("declare") + " " + fun + " " + "\""+ parameter +"\"";


                        }else {
                            line = syntax.syntax.get("declare") + " " + fun + " ";
                        }

                    info.write(line);
                    info.newLine();

                }
                // phase 2 add on - - - - - - - - - - - - - - - - - - -
                String line=syntax.syntax.get("declare")+" "+"PRODUCT_VERSION"+" "+"\""+syntax.version+"\"";
                info.write(line);
                info.newLine();
                line=syntax.syntax.get("declare")+" "+"VERSION"+" "+"\""+syntax.version+"\"";
                info.write(line);
                info.newLine();
                // phase 2 add on syntaxes
                line=syntax.phase_2_addon_u_f.get("product_version").syntax+" "+"\""+"${VERSION}"+"\"";
                info.write(line);
                info.newLine();
                line=syntax.phase_2_addon_u_f.get("file_version").syntax+" "+"\""+"${VERSION}"+"\"";
                info.write(line);
                info.newLine();


                line=syntax.phase_2_addon_u_f.get("manifest_v").syntax+" "+"\""+"FileVersion"+"\"" +" "+"\""+"${VERSION}"+"\"";
                info.write(line);
                info.newLine();

                line=syntax.phase_2_addon_u_f.get("manifest_v").syntax+" "+"\""+"LegalCopyright"+"\""+" "+"\""+syntax.copy_right+"\"";
                info.write(line);
                info.newLine();
                line=syntax.phase_2_addon_u_f.get("manifest_v").syntax+" "+"\""+"FileDescription"+"\""+" "+"\""+"Developed by "+syntax.author+" "+syntax.email+"\"";
                info.write(line);
                info.newLine();









                phase=3;
            }
            else if(phase ==3){
                // phase 3 addon
                String line=syntax.syntax.get("declare_function")+" "+".onInit";
                info.write(line);
                info.newLine();
                //----
                symbol_param syn=syntax.universal_func.get("execute");

                line=syn.syntax+" "+"\""+syntax.params.get(syn.is_have_param)+"\"";
                info.write(line);
                info.newLine();




                //----

                info.write(syntax.syntax.get("close_function"));
                info.newLine();


                //----on init body













                 line=syntax.syntax.get("declare_function")+" "+"finishpageaction";
                info.write(line);
                info.newLine();
                if(is_desktop_allow || is_taskbar_allow) {
                    for(String exes:syntax.executables) {
                        if(exes.equals(" ")){
                           continue;
                        }
                        symbol_param mid = syntax.universal_func.get("create_shortcut");
                        String func = mid.syntax;
                        int parax = mid.is_have_param;
                        String new_subroutine = func + " " + "\"" + "$desktop\\"+exes.replaceAll(".exe",".lnk") + "\"" + " " + "\"" +"$INSTDIR\\"+exes+ "\"";
                        info.write(new_subroutine);
                        info.newLine();

                        if(is_taskbar_allow) {
                            String new_subroutine2 = func + " " + "\"" + "$QUICKLAUNCH\\" + exes.replaceAll(".exe", ".lnk") + "\"" + " " + "\"" + "$INSTDIR\\" + exes + "\"";
                            info.write(new_subroutine2);
                            info.newLine();
                        }
                    }


                }


                info.write(syntax.syntax.get("close_function"));
                info.newLine();
                for(String gen: syntax.phase3){

                    symbol_param mid=syntax.predefined_func.get(gen);
                    String fun=mid.syntax;
                    int  param=mid.is_have_param;

                    String parameter = syntax.params.get(syntax.predefined_func.get(gen).is_have_param);
                     line = "";
                    if(param!=0 && param!=8) {
                        line = syntax.syntax.get("declare") + " " + fun + " " + "\"" + parameter + "\"";
                    }else if(param==8){
                        line = syntax.syntax.get("declare") + " " + fun + " " +  parameter;
                    }
                    else {
                        line = syntax.syntax.get("declare") + " " + fun + " ";
                    }

                    info.write(line);
                    info.newLine();

                }




                phase=4;

            }else  if(phase==4){
                for(String gen: syntax.phase4){


                    symbol_param mid=syntax.universal_func.get(gen);
                    String fun=mid.syntax;
                    int param=mid.is_have_param;

                    String parameter = syntax.params.get(syntax.universal_func.get(gen).is_have_param);
                    String line = "";
                    if(param!=0 && param!=11 && param!=14 && param!=15) {
                        line = fun + " " + "\"" + parameter + "\"";
                    }else if(param==11 || param==14 || param==15){
                        line = fun + " "  + parameter;

                    }
                    else {
                        line =  fun + " ";
                    }





                    info.write(line);
                    info.newLine();

                }




                phase=5;


            }else if(phase==5){
                String line=syntax.syntax.get("declare")+" "+syntax.predefined_func.get("to_abort_warning").syntax;
                info.write(line);
                info.newLine();




                phase=6;

            }else if(phase==6){
                for(String gen: syntax.phase6){
                    if(gen=="-1"){
                        continue;
                    }


                    symbol_param mid=syntax.pages_function.get(gen);
                    String fun=mid.syntax;
                    int param=mid.is_have_param;

                    String parameter = syntax.params.get(syntax.pages_function.get(gen).is_have_param);

                    String line = "";
                    if(param!=0) {
                        line = syntax.syntax.get("macro")+" "+fun + " " + "\"" + parameter + "\"";
                    }else {
                        line = syntax.syntax.get("macro")+" "+ fun + " ";
                    }

                    info.write(line);
                    info.newLine();

                }
                phase=7;

            }else  if(phase==7){
                String line=syntax.syntax.get("individual_microservice")+" "+"\"\"";
                info.write(line);
                info.newLine();
                for(String gen: syntax.phase7){

                    if(gen.equals("WriteUninstaller") && syntax.data.uninstaller_ability[0]==false){
                        continue;


                    }


                    symbol_param mid=syntax.universal_func.get(gen);
                    String fun=mid.syntax;
                    int param=mid.is_have_param;

                    String parameter = syntax.params.get(syntax.universal_func.get(gen).is_have_param);
                    line = "";
                    if(param!=0 && param!=19 && param!=20 && param!=29 && param!=30) {

                        line = fun + " " + "\"" + parameter + "\"";

                    }else if(param==19){
                        line = fun + " " + "\"" + parameter+"\""+"*.*";

                    }
                    else if(param==20){
                        line = fun + " " + parameter+" "+"\""+"Install_Dir"+"\""+" "+"\""+"$INSTDIR"+"\"";

                    }else if(param==29  ){
                        if(syntax.data.uninstaller_ability[0]==false){
                            continue;
                        }
                        line=fun+" "+"\""+parameter+"\""+" "+"\""+"DisplayName"+"\""+" "+"\""+syntax.title+"\"";


                    }else if(param==30){
                        if( syntax.data.uninstaller_ability[0]==false){
                            continue;
                        }
                        line=fun+" "+"\""+parameter+"\""+" "+"\""+"UninstallString"+"\""+" "+"\""+"$INSTDIR\\Uninstall.exe"+"\"";

                    }
                    else {
                        line =  fun + " ";
                    }

                    info.write(line);
                    info.newLine();


                }

                line=syntax.syntax.get("individual_microservice_end");
                info.write(line);
                info.newLine();
                //--------start menu
                line=syntax.syntax.get("individual_microservice")+" "+"\""+"Start Menu Shortcuts"+"\"";
                info.write(line);
                info.newLine();
                //---------------
                symbol_param temp=syntax.universal_func.get("create_directory");
                line=temp.syntax+" "+ "\""+syntax.params.get(temp.is_have_param)+"\"";
                info.write(line);
                info.newLine();
                temp=syntax.universal_func.get("create_uninstaller_shortcut");
                line=temp.syntax+" "+ "\""+syntax.params.get(temp.is_have_param)+"\""+" "+"\""+"$INSTDIR\\uninstall.exe"+"\"";
                info.write(line);
                info.newLine();

                for(String i: syntax.executables){
                    if(!i.equals(" ")){

                        line = syntax.universal_func.get("create_shortcut").syntax+" "+ "\""+"$SMPROGRAMS\\"+syntax.data.title[0]+"\\"+i.replaceAll(".exe",".lnk")+""+"\""+" "+"\""+"$INSTDIR\\"+i+"\"";
                        info.write(line);
                        info.newLine();
                    }
                }





                //-----------
                line=syntax.syntax.get("individual_microservice_end");
                info.write(line);
                info.newLine();






                phase=8;
            }
            else if(phase==8){
                String line=syntax.syntax.get("individual_microservice")+" "+"\"Uninstall\"";

                info.write(line);
                info.newLine();
                for(String gen: syntax.phase8){


                    symbol_param mid=syntax.universal_func.get(gen);
                    String fun=mid.syntax;
                    int param=mid.is_have_param;

                    String parameter = syntax.params.get(syntax.universal_func.get(gen).is_have_param);
                    line = "";

                    if(param!=0 ) {
                        line = fun + " " + "\"" + parameter + "\"";
                    }
                    else {
                        line =  fun + " ";
                    }
                    if(parameter!="#") {
                        info.write(line);
                        info.newLine();
                    }
                    if(parameter=="#"){
                        for(String i: syntax.executables){
                            if(!i.equals(" ")){

                                line = fun + " " + "\"" + "$INSTDIR"  + "\\" + i+"\"";
                                info.write(line);
                                info.newLine();
                            }
                        }


                    }



                }







                line=syntax.syntax.get("individual_microservice_end");
                info.write(line);
                info.newLine();




                phase=9;

            }




            if(phase==9){
                break;
            }
        }

        info.close();
        fstream.close();






    }
    private  void create_env() throws Exception{
        // if .nsi file does not exist create it
        // if exist, make it clean
        custom_io io=new custom_io();
        int val=io.create_file("ide.nsi",path);
        if(val==2){
            io.clearTheFile("ide.nsi",path);

        }


    }


}
