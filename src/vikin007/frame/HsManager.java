package vikin007.frame;

import vikin007.bean.Appointment;
import vikin007.bean.Department;
import vikin007.bean.Doctor;
import vikin007.bean.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/*
1、科室管理：新增科室，删除科室（如果有医生在，则不能删除该科室），修改科室。
2、医生管理：录入医生信息，以及科室信息。修改医生信息（主要是修改个人信息和科室）
3、坐诊信息设置：可以设置医生当天和未来6天的坐诊情况，包括上午和下午的坐诊时间段和可预约数量，系统将自动保存到该医生的坐诊信息列表中。
4、全部信息展示：按照科室，展示每个医生七天的坐诊情况，需要按照科室归类展示
5、预约功能：用户可以选择要预约的科室，医生、日期和时间段，并输入患者的个人信息，系统将自动判断该时间段是否还有预约名额，并保存预约信息。
6. 搜索功能：用户可以输入搜索日期和时间段，系统将自动搜索未来七天内在该时间段坐诊的医生信息，并按照科室分类展示。
7、可以查询某个医生未来七天，病人对它的预约情况。
*/
public class HsManager {
    private ArrayList<Department> departments = new ArrayList<>();
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Schedule> schedules = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();



    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }

    //无参构造器
    public HsManager(){

    }

    //欢迎界面
    public void homePage(){
        Scanner sc = new Scanner(System.in);
        System.out.println("====欢迎进入仁爱医院信息管理系统====");
        System.out.println("1、科室管理-添加科室");
        System.out.println("2、科室管理-删除科室");
        System.out.println("3、科室管理-修改科室");
        System.out.println("4、医生管理-录入医生");
        System.out.println("5、医生管理-医生坐诊设置(可设置当天和未来6天的坐诊情况)");
        System.out.println("6、医生管理-展示全部医生的坐诊详情（当前和未来6天的坐诊详情）");
        System.out.println("7、医生管理-挂号预约");
        System.out.println("8、搜索某个医生当前和未来6天内的病人预约详情（展示每天预约病人的具体信息）");
        System.out.println("9、添加测试数据");
        System.out.println("请输入操作命令");
        switch (sc.nextLine()){
            case "1":addDepartment();
                break;
                case "2":deleteDepartment();
                    break;
                    case "3":alterDepartment();
                        break;
                        case "4":addDoctor();
                            break;
            case "5":setDoctorSchedule();
            break;
            case "6":showScheduleAll();
            break;
            case "7":makeAppointment();
            break;
            case "8":searchAppointmentByDoctor();
            break;
            case "9":demoData();
            break;
            default:break;
        }
    }

    //科室管理-添加科室
    public void addDepartment(){
        Scanner sc = new Scanner(System.in);
        String dpName;
        boolean flag;
        while (true) {
            System.out.println("请输入科室名：");
            dpName = sc.nextLine();
            flag = true;
            for (Department department : departments) {
                if (department.getDepartmentName().equals(dpName)) {
                    System.out.println("科室已存在");
                    flag = false;
                }
            }
            if (flag) {
                departments.add(new Department(dpName));
                break;
            }

        }
            //Department dp = new Department(dpName);

        System.out.println("科室添加成功！");
        homePage();

    }

    //科室管理-删除科室
    public void deleteDepartment(){
        if (!departments.isEmpty()) {
            showDepartment();
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入需要删除的科室序号，放弃请输入0");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice != 0) {
                if(!departments.get(choice - 1).getDoctors().isEmpty()){
                    System.out.println("该科室有医生，无法删除");
                }
                else{
                    departments.remove(choice-1);
                    System.out.println("该科室已被删除");
                }
            }
        }
        else System.out.println("系统中没有任何科室！");
        homePage();
    }

    //呈现科室列表
    public void showDepartment(){
        AtomicInteger ai = new AtomicInteger(0);
        departments.forEach(department -> System.out.println(ai.incrementAndGet()+"."+department.getDepartmentName()));
    }
    //选择科室并呈现所属医生
    public Doctor fromDepartmentToShowDoctors(){

        Scanner sc = new Scanner(System.in);
        System.out.println("请选择科室：");
        showDepartment();
        System.out.println("请输入");
        int code = Integer.parseInt(sc.nextLine());
        System.out.println("当前科室的医生如下：");

        AtomicInteger count = new AtomicInteger(0);
        //count.set(0);

        Doctor selectedDoctor;
        ArrayList<Doctor> selectedDoctors = new ArrayList<>();

        for (Doctor doctor : doctors)
            if (doctor.getDepartmentName().equals(departments.get(code - 1).getDepartmentName())) {
                System.out.println(count.incrementAndGet() + "." + doctor.getName());
                selectedDoctors.add(doctor);
            }
        System.out.println("请输入");
        code = Integer.parseInt(sc.nextLine());
        selectedDoctor = selectedDoctors.get(code-1);
        return selectedDoctor;


    }

    //科室管理-修改科室
    public void alterDepartment(){
        if (!departments.isEmpty()) {
            showDepartment();
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入需要修改的科室序号：（放弃请输入0）");
            int choice = Integer.parseInt(sc.nextLine());
            if(choice != 0){
                System.out.println("请输入新的科室名称：");
                String departmentName = sc.nextLine();
                if(departmentName.equals(departments.get(choice-1).getDepartmentName())){
                    System.out.println("名称相同，无需修改！");
                }
                else{
                    for (Department department : departments) {
                        if (departmentName.equals(department.getDepartmentName())) {
                            System.out.println("跟已有科室名称重复，修改失败！");
                            return;
                        }
                    }
                    departments.get(choice-1).setDepartmentName(departmentName);
                    System.out.println("科室名修改成功！");
                }
            }
        }
        else System.out.println("系统中没有任何科室！");

        homePage();
    }

    //医生管理-录入医生
    public void addDoctor(){
        Scanner sc = new Scanner(System.in);
        System.out.println("========录入医生========");
        if(departments.isEmpty()){
            System.out.println("科室不存在，无法添加医生");
        }
        else{
            AtomicInteger count = new AtomicInteger(0);
            System.out.println("请选择科室：");
            departments.forEach(department -> System.out.println(count.incrementAndGet() +"."+department.getDepartmentName()));

            System.out.println("请输入：");
            int code = Integer.parseInt(sc.nextLine());

            System.out.println("请输入医生姓名：");
            String name = sc.nextLine();
            System.out.println("请输入医生的性别：");
            char gender = sc.nextLine().charAt(0);
            System.out.println("请输入医生的年龄：");
            int age = Integer.parseInt(sc.nextLine());
            System.out.println("请输入医生的特长：");
            String specialty = sc.nextLine();
            System.out.println("请输入医生的入职日期（格式：yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(sc.nextLine());

//            Doctor dcNew = new Doctor(generateID(),name,departments.get(code-1).getDepartmentName(),gender,age,specialty,localDate);
//
//            doctors.add(dcNew);
//
//            departments.get(code-1).getDoctors().add(dcNew);

//            Doctor dcNew2 = Doctor.newDoctor(generateID(),name,departments.get(code-1).getDepartmentName(),gender,age,specialty,localDate,this,code-1);

            Doctor.newDoctor(generateID(),name,departments,gender,age,specialty,localDate,doctors,code-1);

            System.out.println("录入医生到该科室成功");
            doctors.forEach(doctor -> System.out.println(doctor.getDoctorId()+" "+doctor.getName()));

            homePage();

        }

    }

    //处理医生ID问题
    public String generateID(){
        int maxID = doctors.size()+1;
        return String.format("%04d",maxID);
    }

    //医生坐诊设置
    public void setDoctorSchedule(){
        if(departments.isEmpty()){
            System.out.println("尚没有科室和医生！");
        }
        else{
            //科室 -> 医生 选择
            Doctor selectedDoctor = fromDepartmentToShowDoctors();
//            Scanner sc = new Scanner(System.in);
//            System.out.println("请选择科室：");
//            showDepartment();
//            AtomicInteger count = new AtomicInteger(0);
//            departments.forEach(department -> System.out.println(count.incrementAndGet() +"."+department.getDepartmentName()));
//            System.out.println("请输入");
//            int code = Integer.parseInt(sc.nextLine());
//            System.out.println("当前科室的医生如下：");
//
//            count.set(0);
//
//            Doctor selectedDoctor;
//            ArrayList<Doctor> selectedDoctors = new ArrayList<>();
//            String selectID="";
//            for (Doctor doctor : doctors)
//                if (doctor.getDepartmentName().equals(departments.get(code - 1).getDepartmentName())) {
//                    System.out.println(count.incrementAndGet() + "." + doctor.getName());
//                    selectedDoctors.add(doctor);
//                }
//
//
//            System.out.println("请输入");
//            code = Integer.parseInt(sc.nextLine());
//            selectedDoctor = selectedDoctors.get(code-1);
            //科室 -> 医生选择结束

            LocalDate ld = LocalDate.now();

            showOneDoctorSchedules(selectedDoctor,ld,true,"",false);
//            for (int j=0;j<7;j++) {
//                System.out.println(ld +"的安排如下：");
//                if(isDuty(selectedDoctor,ld)){
//
//                        Schedule schedule = giveSchedule(selectedDoctor,ld,"AM");
//                        if(schedule == null){
//                            System.out.println("上午 休息");
//                        }
//                        else{
//
//                            showSchedule(schedule,"上午");
//
//                        }
//
//                        schedule = giveSchedule(selectedDoctor,ld,"PM");
//                        if(schedule == null){
//                            System.out.println("下午 休息");
//                        }
//                        else {
//                            showSchedule(schedule,"下午");
//
//                        }
//
//                }
//                else{
//                    System.out.println("未排班");
//                }
//
//                //设置
//                System.out.println("是否修改(y/n)");
//                char Code = sc.nextLine().charAt(0);
//                if(Code=='y'){
//                    modifySchedule(selectedDoctor,ld);
//                }
//                ld = ld.plusDays(1);
//            }
        }
        homePage();
    }

    //呈现医生的某半天的schedule安排（和giveSchedule配合使用）
    public void showSchedule(Schedule schedule,String amPm){
        //ArrayList<Schedule> schedules = doctor.getSchedules();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.print(amPm+" 坐班 ");
        System.out.print(schedule.getSlt().format(formatter)+"-"+schedule.getElt().format(formatter)+" ");
        System.out.print("总数/已预约 ");
        System.out.println(schedule.getAppointmentCapacity()+"/"+schedule.getAppointments().size());
    }

    //返回医生上午或下午的schedule对象
    public Schedule giveSchedule(Doctor doctor,LocalDate ld,String amPm){
        for(Schedule schedule : doctor.getSchedules()){
            if(schedule.getLdt().isEqual(ld) && schedule.getAmPm().equals(amPm)){
                return schedule;
            }
        }
        return null;
    }

    //显示一个医生的7天全部日程安排
    public void showOneDoctorSchedules(Doctor doctor,LocalDate date,boolean isModify,String header,boolean isAppointment){
        if(!header.isEmpty()){
            System.out.println(doctor.getName()+"医生"+header);
        }

        for(int i=0;i<7;i++){
            System.out.println(date);
            Schedule scheduleAm,schedulePm;
            if(isDuty(doctor,date)){
                scheduleAm = giveSchedule(doctor,date,"AM");

                if(scheduleAm!=null){
                    showSchedule(scheduleAm,"上午");
                }
                else {
                    System.out.println("上午  休息");
                }

                schedulePm = giveSchedule(doctor,date,"PM");
                if(schedulePm!=null){
                    showSchedule(schedulePm,"下午");
                }
                else {
                    System.out.println("下午  休息");
                }


                //激活挂号功能
                if(isAppointment){

                        if(scheduleAm != null || schedulePm != null){
                            Scanner sc = new Scanner(System.in);
                            System.out.println("是否预约本时段？y/n");
                            char Code = sc.nextLine().charAt(0);
                            if(Code=='y'){
                                if(scheduleAm !=null && scheduleAm.getAppointments().size() == scheduleAm.getAppointmentCapacity()){
                                    System.out.println("上午时段已预约满");
                                }
                                else if(scheduleAm!=null){

                                    appointmentDetail(scheduleAm,doctor,"上午");
//                                System.out.println("是否预约上午时段？y/n");
//                                char Code = sc.nextLine().charAt(0);
//                                if(Code=='y'){
//                                    System.out.println("请输入您的姓名：");
//                                    String patientName = sc.nextLine();
//                                    System.out.println("请输入您的证件号：");
//                                    String patientID = sc.nextLine();
//
//                                    //判断患者在本时间段是否有重复的号，如有，则无法挂号。
//                                    boolean canAppointment = true;
//                                    for(Appointment app :scheduleAm.getAppointments()){
//                                        if(app.getPatientID().equals(patientID)){
//                                            System.out.println("您在该时段已有号，无法重复挂号");
//                                            canAppointment = false;
//                                            //break outerloop;
//                                        }
//                                    }
//                                    if(canAppointment){
//                                        Appointment app = new Appointment(patientName,patientID,doctor,scheduleAm);
//                                        scheduleAm.getAppointments().add(app);
//                                        System.out.println("预约成功");
//                                    }
//
//                                    //break;
//                                }
                                }

                                if(schedulePm != null && schedulePm.getAppointments().size() == schedulePm.getAppointmentCapacity()){
                                    System.out.println("下午时段预约已满");
                                }
                                else if(schedulePm!=null){
                                        appointmentDetail(schedulePm,doctor,"下午");
//                                    System.out.println("请输入您的姓名：");
//                                    String patientName = sc.nextLine();
//                                    System.out.println("请输入您的证件号：");
//                                    String patientID = sc.nextLine();
//
//                                    //判断患者在本时间段是否有重复的号，如有，则无法挂号。
//                                    for(Appointment app :schedulePm.getAppointments()){
//                                        if(app.getPatientID().equals(patientID)){
//                                            System.out.println("您在该时段已有号，无法重复挂号");
//                                            //break outerloop;
//                                        }
//                                    }
//                                    Appointment app = new Appointment(patientName,patientID,doctor,scheduleAm);
//                                    schedulePm.getAppointments().add(app);
//
//                                    System.out.println("预约成功！");
                                        //break;

                                }
                            }




                        }



                }

            }
            else{
                System.out.println("未排班");
            }

            //是否激活修改功能
            if(isModify){
                Scanner sc = new Scanner(System.in);
                System.out.println("是否修改(y/n)");
                char Code = sc.nextLine().charAt(0);
                if(Code=='y'){
                    modifySchedule(doctor,date);
                }
            }



            date = date.plusDays(1);
        }
    }

    //挂号细节
    public void appointmentDetail(Schedule schedule,Doctor doctor,String amPm){
        System.out.println("是否预约"+amPm+"时段？y/n");
        Scanner sc = new Scanner(System.in);
        char Code = sc.nextLine().charAt(0);
        if(Code=='y'){
            System.out.println("请输入您的姓名：");
            String patientName = sc.nextLine();
            System.out.println("请输入您的证件号：");
            String patientID = sc.nextLine();

            //判断患者在本时间段是否有重复的号，如有，则无法挂号。
            boolean canAppointment = true;
            for(Appointment app :schedule.getAppointments()){
                if(app.getPatientID().equals(patientID)){
                    System.out.println("您在该时段已有号，无法重复挂号");
                    canAppointment = false;
                    //break outerloop;
                }
            }
            if(canAppointment){
                Appointment app = new Appointment(patientName,patientID,doctor,schedule);
                schedule.getAppointments().add(app);
                System.out.println("预约成功");
            }

            //break;
        }
    }

    //判断某天是否排班
    public boolean isDuty(Doctor doctor,LocalDate date){
        ArrayList<Schedule> schedules = doctor.getSchedules();

        for (Schedule schedule : schedules) {
            if(schedule.getLdt().isEqual(date)){
                return true;
            }
        }

        return false;
    }

    //判断医生某半天是否休息（弃用）
    public boolean isRest(Doctor doctor,LocalDate date,String amPm){


        ArrayList<Schedule> schedules = doctor.getSchedules();


        for (Schedule schedule : schedules) {
            if (schedule.getAmPm().equals(amPm)) {
                return true;
            }
        }


        return false;
    }



    //取得医生某半天的已被预约人数（暂时弃用）
    public int appointmentCount(Doctor doctor,LocalDate date,String amPm){
        int appointmentCount = 0;
        ArrayList<Schedule> schedules = doctor.getSchedules();
        //ArrayList<Appointment> appointments = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getAmPm().equals(amPm) && schedule.getLdt().isEqual(date)) {
                appointmentCount++;
            }
        }

        return appointmentCount;
    }

    //医生排班修改（主方法）
    public void modifySchedule(Doctor doctor,LocalDate date){
        Scanner sc = new Scanner(System.in);
        //ArrayList<Schedule> schedules = doctor.getSchedules();
        System.out.println("上午是否上班？y/n");
        char code = sc.nextLine().charAt(0);
        //System.out.println("下午是否上班");
        String amPm = "";
        if(code=='y'){
            amPm = "AM";
            modifyScheduleAmPm(doctor,date,amPm);
        }

        System.out.println("下午是否坐班？y/n");
        code = sc.nextLine().charAt(0);

        if (code=='y'){
            amPm = "PM";
            modifyScheduleAmPm(doctor,date,amPm);
        }
    }

    //医生排班修改（上午、下午具体修改）
    public void modifyScheduleAmPm(Doctor doctor,LocalDate date,String amPm){

        ArrayList<Schedule> schedules = doctor.getSchedules();
        Scanner sc = new Scanner(System.in);
        System.out.println("上班的开始时间和结束时间是：");
        System.out.println("格式：HH-mm,先后输入开始和结束时间，每次输入需回车确认");
        LocalTime lts = LocalTime.parse(sc.nextLine());
        LocalTime lte = LocalTime.parse(sc.nextLine());
        System.out.println("最大预约人数为：");
        int capacity = Integer.parseInt(sc.nextLine());

        for (Schedule schedule : schedules) {
            if (schedule.getLdt().isEqual(date) && schedule.getAmPm().equals(amPm)) {
                schedule.setSlt(lts);
                schedule.setElt(lte);
                schedule.setAppointmentCapacity(capacity);
                return;
            }
        }
        schedules.add(new Schedule(doctor.getDoctorId(),date,amPm,lts,lte,capacity));
    }

    //所有医生的坐诊详单
    public void showScheduleAll(){
        System.out.println("全部医生的坐诊详情如下===");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i+1)+"."+departments.get(i).getDepartmentName());
            System.out.println("==========================================");
            if(!departments.get(i).getDoctors().isEmpty()){
                ArrayList<Doctor> doctors = departments.get(i).getDoctors();
                LocalDate ld = LocalDate.now();
                for (Doctor doctor : doctors) {
//                    for(int j=0;j<7;j++){
//                        System.out.println(ld);
//                        if(isDuty(doctor,ld)){
//                            Schedule schedule = giveSchedule(doctor,ld,"AM");
//                            if(schedule!=null){
//                                showSchedule(schedule,"AM");
//                            }
//                            else {
//                                System.out.println("上午  休息");
//                            }
//
//                            schedule = giveSchedule(doctor,ld,"PM");
//                            if(schedule!=null){
//                                showSchedule(schedule,"PM");
//                            }
//                            else {
//                                System.out.println("下午  休息");
//                            }
//                        }
//                        else{
//                            System.out.println("未排班");
//                        }
//
//                        ld = ld.plusDays(1);
//                    }
                    //System.out.println(doctor.getName()+"的坐诊信息如下：");
                    showOneDoctorSchedules(doctor,ld,false,"的坐诊信息如下：",false);

                }
            }
        }
        homePage();
    }


    /*
     *以下若干方法用来添加测试用例
     */
    public void demoData(){
        //添加科室
        departments.add(new Department("骨科"));
        departments.add(new Department("内科"));
        departments.add(new Department("外科"));

        LocalDate date = LocalDate.now();
        //LocalTime lts = LocalTime.now();



        //添加医生
        Doctor.newDoctor(generateID(),"骨科张医生",departments,'女',39,"人美心善",LocalDate.parse("2001-11-21"),doctors,0);
        Doctor.newDoctor(generateID(),"骨科李医生",departments,'女',39,"人美心善",LocalDate.parse("2001-11-21"),doctors,0);
        Doctor.newDoctor(generateID(),"内科王医生",departments,'男',39,"人美心善",LocalDate.parse("2001-11-21"),doctors,1);
        Doctor.newDoctor(generateID(),"内科赵医生",departments,'女',39,"人美心善",LocalDate.parse("2001-11-21"),doctors,1);
        Doctor.newDoctor(generateID(),"内科陈医生",departments,'男',39,"人美心善",LocalDate.parse("2001-11-21"),doctors,1);

        //第一个医生的日程
        Schedule sd = new Schedule(doctors.getFirst().getDoctorId(),date,"AM",LocalTime.parse("09:30"),LocalTime.parse("11:30"),15);
        //schedules.add(sd);
        doctors.getFirst().getSchedules().add(sd);
        sd = new Schedule(doctors.getFirst().getDoctorId(),date.plusDays(1),"AM",LocalTime.parse("07:30"),LocalTime.parse("10:30"),19);
        doctors.getFirst().getSchedules().add(sd);
        sd = new Schedule(doctors.get(0).getDoctorId(),date.plusDays(5),"PM",LocalTime.parse("13:30"),LocalTime.parse("17:30"),25);
        doctors.get(0).getSchedules().add(sd);

        //第二个医生的日程
        sd = new Schedule(doctors.get(1).getDoctorId(),date,"AM",LocalTime.parse("09:30"),LocalTime.parse("11:30"),15);
        //schedules.add(sd);
        doctors.get(1).getSchedules().add(sd);
        sd = new Schedule(doctors.get(1).getDoctorId(),date.plusDays(2),"AM",LocalTime.parse("07:30"),LocalTime.parse("10:30"),19);
        doctors.get(1).getSchedules().add(sd);
        sd = new Schedule(doctors.get(1).getDoctorId(),date.plusDays(3),"PM",LocalTime.parse("13:30"),LocalTime.parse("17:30"),25);
        doctors.get(1).getSchedules().add(sd);

        //第五个医生
        sd = new Schedule(doctors.get(4).getDoctorId(),date.plusDays(1),"PM",LocalTime.parse("14:30"),LocalTime.parse("16:30"),13);
        //schedules.add(sd);
        doctors.get(4).getSchedules().add(sd);
        sd = new Schedule(doctors.get(4).getDoctorId(),date.plusDays(4),"AM",LocalTime.parse("07:30"),LocalTime.parse("10:30"),19);
        doctors.get(4).getSchedules().add(sd);
        sd = new Schedule(doctors.get(4).getDoctorId(),date.plusDays(5),"PM",LocalTime.parse("13:30"),LocalTime.parse("17:30"),25);
        doctors.get(4).getSchedules().add(sd);

        System.out.println("测试数据添加成功！");
        homePage();


    }

    //挂号功能
    public void makeAppointment(){
        System.out.println("=========挂号模块===========");
        if(departments.isEmpty() || doctors.isEmpty()) {
            System.out.println("要么没科室，要么没医生，挂个毛的号！");
        }
        else{
            //showDepartment();
            Doctor selectedDoctor = fromDepartmentToShowDoctors();
            showOneDoctorSchedules(selectedDoctor,LocalDate.now(),false,"",true);

        }

        homePage();
    }

    //搜索医生详细的病人预约信息（与以下两种方法配合使用）
    public void searchAppointmentByDoctor(){
        System.out.println("=========医生预约详情========");
        //System.out.println();
        if(doctors.isEmpty()){
            System.out.println("系统中没有医生！");
        }
        else{
            Scanner sc = new Scanner(System.in);
            System.out.println("1、通过医生ID搜索");
            System.out.println("2、通过医生姓名搜索");
            System.out.println("请输入：");
            char code = sc.nextLine().charAt(0);
            if(code == '2'){
                System.out.println("请输入要搜索的医生姓名：");
                String doctorName = sc.nextLine();
                searchByName(doctorName);
            }
            else if(code == '1'){
                System.out.println("请输入要搜索的医生ID");
                String doctorId = sc.nextLine();
                searchById(doctorId);
            }


        }
    }

    //通过ID搜索
    public void searchByName(String doctorName){
//        for(Doctor doctor : doctors){
//            if(doctor.getName().equals(doctorName)){
//                System.out.println("医生ID："+doctor.getDoctorId());
//                System.out.println("医生姓名："+doctor.getName());
//                System.out.println("科室："+doctor.getDepartmentName());
//                LocalDate ld = LocalDate.now();
//                for(int i=0;i<7;i++){
//                    for(Schedule schedule : doctor.getSchedules()){
//                        if(schedule.getLdt().isEqual(ld)){
//                            System.out.println(ld+"预约情况");
//                            if(schedule.getAppointments().isEmpty()){
//                                System.out.println(schedule.getAmPm()+"暂无病人预约");
//                            }
//                            else{
//                                System.out.println(HsDictionary.dictionary.get(schedule.getAmPm())+"预约情况：");
//                                for(Appointment appointment : schedule.getAppointments()){
//                                    System.out.println("患者证件号"+appointment.getPatientID());
//                                    System.out.println("患者姓名："+appointment.getPatientName());
//                                    System.out.println("-------------------------------------");
//                                }
//                            }
//                        }
//                    }
//                    ld= ld.plusDays(1);
//                }
//
//            }
//        }
        for(Doctor doctor : doctors){
            if(doctor.getName().equals(doctorName)){
                searchGeneral(doctor);
            }
        }
        homePage();
    }


    //通过医生姓名搜索
    public void searchById(String doctorId){
        for(Doctor doctor : doctors){
            if(doctor.getDoctorId().equals(doctorId)){
                searchGeneral(doctor);
            }
        }
        homePage();
    }

    public void searchGeneral(Doctor doctor){
        System.out.println("医生ID："+doctor.getDoctorId());
        System.out.println("医生姓名："+doctor.getName());
        System.out.println("科室："+doctor.getDepartmentName());

        LocalDate ld = LocalDate.now();
        for(int i=0;i<7;i++){
            for(Schedule schedule : doctor.getSchedules()){
                if(schedule.getLdt().isEqual(ld)){
                    System.out.println(ld+"预约情况");
//                    if(schedule.getAppointments().isEmpty()){
//                        System.out.println(schedule.getAmPm()+"暂无病人预约");
//                    }

                        System.out.println(HsDictionary.dictionary.get(schedule.getAmPm())+"预约情况：");
                        for(Appointment appointment : schedule.getAppointments()){
                            System.out.println("患者证件号"+appointment.getPatientID());
                            System.out.println("患者姓名："+appointment.getPatientName());
                            System.out.println("-------------------------------------");
                        }

                }
            }
            ld= ld.plusDays(1);
        }
    }


}