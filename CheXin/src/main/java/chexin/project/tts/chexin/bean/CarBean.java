package chexin.project.tts.chexin.bean;

import java.io.Serializable;

/**
 * Created by sjb on 2016/3/25.
 */
public class CarBean implements Serializable {


    /**
     * ID : 1
     * CodeID : fd55403aa8a14f068eda148b3c30521c
     * CarNo : 皖W12233
     * FromProvince : 12
     * FromCity : 108
     * FromCountry : 978
     * ToProvince : 12
     * ToCity : 108
     * ToCountry : 975
     * ExpectedSkuType : 01
     * ExpectedPrice : 3000.0000
     * Company : 十大
     * Address : 阿斯顿撒
     * Contact : 张三
     * MobileNo : 13688776634
     * TelephoneNo : 05002765345
     * ExpiryDate :
     * Status : 2
     * Note : 说明
     * AddDate : 2016-01-04 01:18:48.0
     * EditDate : 2016-01-21 16:42:10.0
     * ReserveString1 : 按时
     * ReserveString2 : 01
     * ReserveString3 :
     * ReserveString4 :
     * ReserveDate1 :
     * ReserveDate2 :
     * ReserveDate3 :
     * ReserveDate4 :
     * ReserveNumber1 :
     * ReserveNumber2 :
     * ReserveNumber3 :
     * ReserveNumber4 :
     * CarVolume : 60.000000
     * CarNo2 :
     * CarOrder :
     * ExpiryType :
     * UserId : 1
     * CarType : 01
     * CarWeight : 50.000000
     * CarLength : 12.000000
     * CarWidth :
     * CarCode :
     * distance :
     * memberBean : {"ID":"1","UserId":"","CodeID":"","CardID":"","CardType":"","CardUserName":"","CardUserAddress":"","CardIsBlack":"","CardComment":"","MobileNo":"13917522657","Deposit":"","Balance":"","AddComment":"","Name":"","Gender":"","Nation":"","Birth":"","Address":"","Deadline":"","IDCardNo":"","UserName":"赵大宝(车主)","Password":"c7Pfa3dGUS0=","Age":"","HomeAddress":"","Province":"","City":"","Country":"","Email":"","Company":"","CompanyProvince":"","CompanyCity":"","CompanyCountry":"","CompanyAddress":"","CompanyType":"","TelephoneNo":"","FaxNo":"","ZipCode":"","UserType":"1","Status":"","RECORDSTATUS":"","AddDate":"","EditDate":"","ReserveString1":"","ReserveString2":"","ReserveString3":"","ReserveString4":"","ReserveDate1":"","ReserveDate2":"","ReserveDate3":"","ReserveDate4":"","ReserveNumber1":"","ReserveNumber2":"","ReserveNumber3":"","ReserveNumber4":"","BoothNo":"","BoothName":"","CarNo":"","DriveNo":"","token":"c969ad93-4efb-44c3-969e-ca49bba8b3b4","CreateDate":"","ModifyDate":"","BlackDate":"","PersonPhoto":"","AddPhoto":"","IDCardPhoto":"","Fingerprint":"","FingerprintPhoto":"","CompanyPic1":"","CompanyPic2":"","CompanyPic3":"","star":"5","head_path":"http://wl.tstmobile.com/ssm//images/user//20160410112458.jpg","hostUrl":""}
     * apply_count :
     */

    private int ID;
    private String CodeID;
    private String CarNo;
    private String FromProvince;
    private String FromCity;
    private String FromCountry;
    private String ToProvince;
    private String ToCity;
    private String ToCountry;
    private String ExpectedSkuType;
    private String ExpectedPrice;
    private String Company;
    private String Address;
    private String Contact;
    private String MobileNo;
    private String TelephoneNo;
    private String ExpiryDate;
    private String Status;
    private String Note;
    private String AddDate;
    private String EditDate;
    private String ReserveString1;
    private String ReserveString2;
    private String ReserveString3;
    private String ReserveString4;
    private String ReserveDate1;
    private String ReserveDate2;
    private String ReserveDate3;
    private String ReserveDate4;
    private String ReserveNumber1;
    private String ReserveNumber2;
    private String ReserveNumber3;
    private String ReserveNumber4;
    private String CarVolume;
    private String CarNo2;
    private String CarOrder;
    private String ExpiryType;
    private String UserId;
    private String CarType;
    private String CarWeight;
    private String CarLength;
    private String CarWidth;
    private String CarCode;
    private String distance;
    private String is_car_collection;
    private String car_collection_id;
    private String is_user_collection;
    private String user_collection_id;
    private String vehicle_id;
    private String vehicle_state;

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getVehicle_state() {
        return vehicle_state;
    }

    public void setVehicle_state(String vehicle_state) {
        this.vehicle_state = vehicle_state;
    }

    private MemberBean memberBean;

    public String getCar_collection_id() {
        return car_collection_id;
    }

    public void setCar_collection_id(String car_collection_id) {
        this.car_collection_id = car_collection_id;
    }

    public String getIs_user_collection() {
        return is_user_collection;
    }

    public void setIs_user_collection(String is_user_collection) {
        this.is_user_collection = is_user_collection;
    }

    public String getUser_collection_id() {
        return user_collection_id;
    }

    public void setUser_collection_id(String user_collection_id) {
        this.user_collection_id = user_collection_id;
    }

    public String getIs_car_collection() {
        return is_car_collection;
    }

    public void setIs_car_collection(String is_car_collection) {
        this.is_car_collection = is_car_collection;
    }

    public MemberBean getMemberBean() {
        return memberBean;
    }

    public void setMemberBean(MemberBean memberBean) {
        this.memberBean = memberBean;
    }

    private String apply_count;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCodeID() {
        return CodeID;
    }

    public void setCodeID(String CodeID) {
        this.CodeID = CodeID;
    }

    public String getCarNo() {
        return CarNo;
    }

    public void setCarNo(String CarNo) {
        this.CarNo = CarNo;
    }

    public String getFromProvince() {
        return FromProvince;
    }

    public void setFromProvince(String FromProvince) {
        this.FromProvince = FromProvince;
    }

    public String getFromCity() {
        return FromCity;
    }

    public void setFromCity(String FromCity) {
        this.FromCity = FromCity;
    }

    public String getFromCountry() {
        return FromCountry;
    }

    public void setFromCountry(String FromCountry) {
        this.FromCountry = FromCountry;
    }

    public String getToProvince() {
        return ToProvince;
    }

    public void setToProvince(String ToProvince) {
        this.ToProvince = ToProvince;
    }

    public String getToCity() {
        return ToCity;
    }

    public void setToCity(String ToCity) {
        this.ToCity = ToCity;
    }

    public String getToCountry() {
        return ToCountry;
    }

    public void setToCountry(String ToCountry) {
        this.ToCountry = ToCountry;
    }

    public String getExpectedSkuType() {
        return ExpectedSkuType;
    }

    public void setExpectedSkuType(String ExpectedSkuType) {
        this.ExpectedSkuType = ExpectedSkuType;
    }

    public String getExpectedPrice() {
        return ExpectedPrice;
    }

    public void setExpectedPrice(String ExpectedPrice) {
        this.ExpectedPrice = ExpectedPrice;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String MobileNo) {
        this.MobileNo = MobileNo;
    }

    public String getTelephoneNo() {
        return TelephoneNo;
    }

    public void setTelephoneNo(String TelephoneNo) {
        this.TelephoneNo = TelephoneNo;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String AddDate) {
        this.AddDate = AddDate;
    }

    public String getEditDate() {
        return EditDate;
    }

    public void setEditDate(String EditDate) {
        this.EditDate = EditDate;
    }

    public String getReserveString1() {
        return ReserveString1;
    }

    public void setReserveString1(String ReserveString1) {
        this.ReserveString1 = ReserveString1;
    }

    public String getReserveString2() {
        return ReserveString2;
    }

    public void setReserveString2(String ReserveString2) {
        this.ReserveString2 = ReserveString2;
    }

    public String getReserveString3() {
        return ReserveString3;
    }

    public void setReserveString3(String ReserveString3) {
        this.ReserveString3 = ReserveString3;
    }

    public String getReserveString4() {
        return ReserveString4;
    }

    public void setReserveString4(String ReserveString4) {
        this.ReserveString4 = ReserveString4;
    }

    public String getReserveDate1() {
        return ReserveDate1;
    }

    public void setReserveDate1(String ReserveDate1) {
        this.ReserveDate1 = ReserveDate1;
    }

    public String getReserveDate2() {
        return ReserveDate2;
    }

    public void setReserveDate2(String ReserveDate2) {
        this.ReserveDate2 = ReserveDate2;
    }

    public String getReserveDate3() {
        return ReserveDate3;
    }

    public void setReserveDate3(String ReserveDate3) {
        this.ReserveDate3 = ReserveDate3;
    }

    public String getReserveDate4() {
        return ReserveDate4;
    }

    public void setReserveDate4(String ReserveDate4) {
        this.ReserveDate4 = ReserveDate4;
    }

    public String getReserveNumber1() {
        return ReserveNumber1;
    }

    public void setReserveNumber1(String ReserveNumber1) {
        this.ReserveNumber1 = ReserveNumber1;
    }

    public String getReserveNumber2() {
        return ReserveNumber2;
    }

    public void setReserveNumber2(String ReserveNumber2) {
        this.ReserveNumber2 = ReserveNumber2;
    }

    public String getReserveNumber3() {
        return ReserveNumber3;
    }

    public void setReserveNumber3(String ReserveNumber3) {
        this.ReserveNumber3 = ReserveNumber3;
    }

    public String getReserveNumber4() {
        return ReserveNumber4;
    }

    public void setReserveNumber4(String ReserveNumber4) {
        this.ReserveNumber4 = ReserveNumber4;
    }

    public String getCarVolume() {
        return CarVolume;
    }

    public void setCarVolume(String CarVolume) {
        this.CarVolume = CarVolume;
    }

    public String getCarNo2() {
        return CarNo2;
    }

    public void setCarNo2(String CarNo2) {
        this.CarNo2 = CarNo2;
    }

    public String getCarOrder() {
        return CarOrder;
    }

    public void setCarOrder(String CarOrder) {
        this.CarOrder = CarOrder;
    }

    public String getExpiryType() {
        return ExpiryType;
    }

    public void setExpiryType(String ExpiryType) {
        this.ExpiryType = ExpiryType;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String CarType) {
        this.CarType = CarType;
    }

    public String getCarWeight() {
        return CarWeight;
    }

    public void setCarWeight(String CarWeight) {
        this.CarWeight = CarWeight;
    }

    public String getCarLength() {
        return CarLength;
    }

    public void setCarLength(String CarLength) {
        this.CarLength = CarLength;
    }

    public String getCarWidth() {
        return CarWidth;
    }

    public void setCarWidth(String CarWidth) {
        this.CarWidth = CarWidth;
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String CarCode) {
        this.CarCode = CarCode;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getApply_count() {
        return apply_count;
    }

    public void setApply_count(String apply_count) {
        this.apply_count = apply_count;
    }


}
