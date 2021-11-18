package core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class symbol {

    public   Hashtable<String,String> syntax=new Hashtable<>();
    public   Hashtable<String,symbol_param> predefined_func=new Hashtable<>();
    public   Hashtable<String,symbol_param> universal_func=new Hashtable<>();
    public   Hashtable<String,symbol_param> pages_function=new Hashtable<>();
    public   String lib="MUI2.nsh";
    public   Hashtable<Integer,String> params=new Hashtable<>();
    public   Hashtable<String,symbol_param> phase_2_addon_u_f=new Hashtable<>();
    public   List<String> phase2 =new ArrayList<>();
    public   List<String> phase2_add_on =new ArrayList<>();

    public   List<String> phase3=new ArrayList<>();
    public   List<String> phase4=new ArrayList<>();
    public   List<String> phase6=new ArrayList<>();
    public   List<String> phase7=new ArrayList<>();
    public   List<String> phase8=new ArrayList<>();

    retrieve_info data=new retrieve_info();
    public  String copy_right="";
    public  String author="";
    public  String email="";
    public  String title="";
    public  String[] executables;
    public  String  version="";









    public void init() throws Exception {
        data.init_data();
       copy_right=data.copy_right[0];
       author=data.author[0];
       email=data.email[0];
       title=data.title[0];
       executables=new String[data.length];
       executables=data.executables;
       version=data.version_setup[0];

        syntax.put("import","!include");
        syntax.put("comment",";");
        syntax.put("declare","!define");
        syntax.put("declare_function","Function");
        syntax.put("close_function","FunctionEnd");
        syntax.put("individual_microservice","Section");
        syntax.put("individual_microservice_end","SectionEnd");
        syntax.put("macro","!insertmacro");

        //--------------------till here syntax
        predefined_func.put("to_create_icon",new symbol_param("MUI_ICON",1));
        predefined_func.put("header_image_helper",new symbol_param("MUI_HEADERIMAGE",0));
        predefined_func.put("header_image_bmp",new symbol_param("MUI_HEADERIMAGE_BITMAP",3));
        predefined_func.put("side_bar_bmp",new symbol_param("MUI_WELCOMEFINISHPAGE_BITMAP",4));
        predefined_func.put("header_image_right_alignment",new symbol_param("MUI_HEADERIMAGE_RIGHT",0));
        predefined_func.put("welcome_page_text",new symbol_param("MUI_WELCOMEPAGE_TEXT",5));
        predefined_func.put("finish_page_text",new symbol_param("MUI_FINISHPAGE_TEXT",6));
        predefined_func.put("to_reference_finish_page",new symbol_param("MUI_FINISHPAGE_SHOWREADME",0));
        predefined_func.put("to_reference_finish_page1",new symbol_param("MUI_FINISHPAGE_SHOWREADME_NOTCHECKED",0));
        predefined_func.put("to_reference_finish_page2",new symbol_param("MUI_FINISHPAGE_SHOWREADME_TEXT",7));
        predefined_func.put("to_link_finish_to_fun",new symbol_param("MUI_FINISHPAGE_SHOWREADME_FUNCTION",8));
        predefined_func.put("to_abort_warning",new symbol_param("MUI_ABORTWARNING",0));
        universal_func.put("name_of_setup",new symbol_param("Name",9));
        universal_func.put("output_name_of_setup",new symbol_param("OutFile",10));
        universal_func.put("to_unicode",new symbol_param("Unicode",11));
        universal_func.put("water_mark",new symbol_param("BrandingText",12));
        universal_func.put("instalation_path",new symbol_param("InstallDir",13));
        universal_func.put("installation_registry",new symbol_param("InstallDirRegKey HKLM",14));
        universal_func.put("request_access",new symbol_param("RequestExecutionLevel",15));
        pages_function.put("welcome_page",new symbol_param("MUI_PAGE_WELCOME",0));
        pages_function.put("license_page",new symbol_param("MUI_PAGE_LICENSE",16));
        pages_function.put("installation_page",new symbol_param("MUI_PAGE_DIRECTORY",0));
        pages_function.put("process_installation_page",new symbol_param("MUI_PAGE_INSTFILES",0));
        pages_function.put("finish_page",new symbol_param("MUI_PAGE_FINISH",0));
        pages_function.put("uninstaller_welcome_page",new symbol_param("MUI_UNPAGE_WELCOME",0));
        pages_function.put("uninstaller_confirmation",new symbol_param("MUI_UNPAGE_CONFIRM",0));
        pages_function.put("uninstaller_process",new symbol_param("MUI_UNPAGE_INSTFILES",0));
        pages_function.put("uninstaller_finish",new symbol_param("MUI_UNPAGE_FINISH",0));
        pages_function.put("language_init",new symbol_param("MUI_LANGUAGE",17));
        // i will write installer_section

        universal_func.put("output_path",new symbol_param("SetOutPath",18));
        universal_func.put("files",new symbol_param("File /r",19));
        universal_func.put("installation_folder_registry",new symbol_param("WriteRegStr HKLM",20));
        //--
        universal_func.put("unstallation_folder_registry",new symbol_param("WriteRegStr HKLM",29));
        universal_func.put("unstallation_folder_registry_2",new symbol_param("WriteRegStr HKLM",30));



        //---
        universal_func.put("WriteUninstaller",new symbol_param("WriteUninstaller",21));
        universal_func.put("Delete_file",new symbol_param("Delete",22));
        universal_func.put("Delete_file_2",new symbol_param("Delete",31));
        universal_func.put("Delete_file_3",new symbol_param("Delete",32));
        universal_func.put("remove_dir",new symbol_param("RMDir /r",23));
        universal_func.put("remove_registry_uninstall",new symbol_param("DeleteRegKey HKLM",24));
        universal_func.put("create_shortcut",new symbol_param("CreateShortcut",25));
        universal_func.put("create_directory",new symbol_param("CreateDirectory",34));

        universal_func.put("execute",new symbol_param("Exec",33));
        universal_func.put("create_uninstaller_shortcut",new symbol_param("CreateShortcut",35));

        //--------------------------------------------------------------------------------------------------------
         // for testing purposes
        phase_2_addon_u_f.put("product_version",new symbol_param("VIProductVersion",26));
        phase_2_addon_u_f.put("file_version",new symbol_param("VIFileVersion",27));
        phase_2_addon_u_f.put("manifest_v",new symbol_param("VIAddVersionKey",28));






        params.put(1,data.icon_path[0]);
        params.put(3,data.bmp_path[0]);
        params.put(4,data.bmp_path[0]);
        params.put(5,data.welcome_msg[0]);
        params.put(6,data.final_screen_msg[0]);
        if(data.desktop_shortcut_ability[0]) {
            params.put(7,"Add desktop shortcut");

        }
        if(data.task_bar_pin_ability[0]){
            params.put(7,"pin to the taskbar");
        }
        if(data.desktop_shortcut_ability[0] && data.task_bar_pin_ability[0]){
            params.put(7,"Add desktop and taskbar shortcut");
        }


        params.put(8,"finishpageaction");
        params.put(9,data.title[0]);
        params.put(10,data.output_path[0]+"\\"+data.title[0]+".exe");
        params.put(11,"true");
        if(data.watermark[0]){
            params.put(12,"Built with PAQT installer builder engine");
        }else{
            params.put(12,data.author[0]);
        }
        params.put(13,data.path[0]+"\\"+data.folder_name[0]);
        String reg_key="software"+"\\"+data.title[0];
        params.put(14,"\""+reg_key+"\""+" "+"\"$INSTDIR\"");
        params.put(15,"admin");
        String path=  System.getProperty("user.dir")+"\\"+"custom_action\\";
        params.put(16,path+"license.txt");
        params.put(17,"English");
        params.put(18,"$INSTDIR");
        params.put(19,data.files_path[0]);
        params.put(20,"\""+reg_key+"\"");
        params.put(21,"$INSTDIR\\Uninstall.exe");
        params.put(22,"$INSTDIR\\Uninstall.exe");
        params.put(23,"$INSTDIR");
        reg_key="Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\"+title;
        params.put(24,reg_key);
        params.put(25,"$desktop\\"+data.title[0]+".lnk"); // 25 is deprecated
        params.put(26,"${PRODUCT_VERSION}");
        params.put(27,"${VERSION}");
        params.put(29,"Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\"+data.title[0]);
        params.put(30,"Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\"+data.title[0]);
        params.put(31,"#");
        params.put(32,"$SMPROGRAMS\\"+title+"\\*.lnk");


        params.put(33,path+"action.bat");
        params.put(34,"$SMPROGRAMS\\"+data.title[0]);
        params.put(35,"$SMPROGRAMS\\"+data.title[0]+"\\"+"uninstall"+" "+data.title[0]+ ".lnk");




        //@todo i have to multople executable concepts












        //----------------------------------------

        phase2.add("to_create_icon");
        phase2.add("header_image_helper");
        phase2.add("header_image_bmp");
        phase2.add("side_bar_bmp");
        phase2.add("header_image_right_alignment");
        phase2.add("welcome_page_text");
        phase2.add("finish_page_text");
        phase3.add("to_reference_finish_page");
        phase3.add("to_reference_finish_page1");
        phase3.add("to_reference_finish_page2");
        phase3.add("to_link_finish_to_fun");
        phase4.add("name_of_setup");
        phase4.add("output_name_of_setup");
        phase4.add("to_unicode");
        phase4.add("water_mark");
        phase4.add("instalation_path");
        phase4.add("installation_registry");
        phase4.add("request_access");
        phase6.add("welcome_page");
        phase6.add("license_page");
        if(data.is_user_allowed_to_change_directory[0]) {
            phase6.add("installation_page");
        }else{
            phase6.add("-1");

        }
        phase6.add("process_installation_page");
        phase6.add("finish_page");
        phase6.add("uninstaller_welcome_page");
        phase6.add("uninstaller_confirmation");
        phase6.add("uninstaller_process");
        phase6.add("uninstaller_finish");
        phase6.add("language_init");
        phase7.add("output_path");
        phase7.add("files");
        phase7.add("installation_folder_registry");

        phase7.add("WriteUninstaller");

        phase8.add("Delete_file");
        phase8.add("Delete_file_2");

        phase8.add("remove_dir");
        phase8.add("remove_registry_uninstall");
        phase8.add("Delete_file_3");
        phase7.add("unstallation_folder_registry");
        phase7.add("unstallation_folder_registry_2");
































    }
}

class symbol_param{

    String syntax;
    int is_have_param;
    symbol_param(String syntax,int is_have_param){
        this.syntax=syntax;
        this.is_have_param=is_have_param;
    }




}

// --- we can write code some how by simulating phases
/***
 * can i say
 *  1- it should import library first
 *  2- startworking on predefined function
 *
 *
 *
 *
 *
 * */



