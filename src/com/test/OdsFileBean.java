package com.test;

public class OdsFileBean{
	private char               RECORD_TYPE    ;     
	private char               NI_PDP         ;
	private char               MSNC           ;
	private char               PDP_TYPE       ;
	private Character               SGSN_CHANGE    ;
	private String               CAUSE_CLOSE    ;
	private char               RESULT         ;
	private char               ROAM_TYPE      ;
	private char               TARIFF1        ;
	private char               TARIFF2        ;
	private char               TARIFF3        ;
	private char               TARIFF4        ;
	private char               TARIFF5        ;
	private char               TARIFF6        ;
	private char               RATEMETHOD     ;
	private String             DEAL_TIME      ;
	private String             USER_TYPE      ;
	private String             FEE_TYPE       ;
	private String             A_SERV_TYPE    ;
	private String             SERVICE_TYPE   ;
	private String             OFFICE_CODE    ;
	private String             START_TIME     ;
	private String             START_DATE     ;
	private Short              PAY_MODE       ;
	private Long               DATA_UP1       ;
	private Long               DATA_DOWN1     ;
	private Long               DATA_UP2       ;
	private Long               DATA_DOWN2     ;
	private Long               DATA_UP3       ;
	private Long               DATA_DOWN3     ;
	private Long               DATA_UP4       ;
	private Long               DATA_DOWN4     ;
	private Long               DATA_UP5       ;
	private Long               DATA_DOWN5     ;
	private Long               DATA_UP6       ;
	private Long               DATA_DOWN6     ;
	private Long               CFEE           ;
	private Long               FEE1           ;
//	private Long               FEE2           ;
	private Long               FEE2           ;
	private Long               FEE3           ;
	private Long               TOTAL_FEE      ;
	private Long               FREE1          ;
	private Long               FREE2          ;
	private Long               FREE3          ;
	private Long               FREE4          ;
	private Long               USER_ID        ;
	private short              REVISION       ;
	private Long               FILE_NAME      ;
	private int                A_BRAND_ID     ;
	private int                CHANNEL_NO     ;
	private int                FREE_ITEM1     ;
	private int                FREE_ITEM2     ;
	private int                FREE_ITEM3     ;
	private int                FREE_ITEM4     ;
	private int                CALL_DURATION  ;
	private int                DURATION1      ;
	private int                DURATION2      ;
	private int                DURATION3      ;
	private int                DURATION4      ;
	private int                DURATION5      ;
	private int                DURATION6      ;
	private int                RCDSEQNUM      ;
	private int                FREE_FEE1      ;
	private int                FREE_FEE2      ;
	private int                FREE_FEE3      ;
	private int                FREE_FEE4      ;
	private int                BILL_START_DAY ;
	private int                BILL_END_DAY   ;
	private int                CDR_DAY        ;
	private String             HOME_AREA_CODE ;
	private String             VISIT_AREA_CODE;
	private String             FREE_CODE1     ;
	private String             FREE_CODE2     ;
	private String             FREE_CODE3     ;
	private String             FREE_CODE4     ;
	private String             PRODUCT_ID1    ;
	private String             INSTANCE_ID1   ;
	private String             PRODUCT_ID2    ;
	private String             INSTANCE_ID2   ;
	private String             PRODUCT_ID3    ;
	private String             INSTANCE_ID3   ;
	private String             PRODUCT_ID4    ;
	private String             INSTANCE_ID4   ;
	private String             RATE_ITEM_ID1  ;
	private String             RATE_ITEM_ID2  ;
	private String             RATE_ITEM_ID3  ;
	private String             RATE_ITEM_ID4  ;
	private String             RATE_TIME      ;
	private String             ACC_TIME       ;
	private String             DIS_TIME       ;
	private String             MSISDN         ;
	private String             IMSI_NUMBER    ;
	private String             SGSN           ;
	private String             GGSN           ;
	private String             SPA            ;
	private String             CUST_ID        ;
	private String             IMEI           ;
	private String             ACCU_USER_ID1  ;
	private String             ACCU_USER_ID2  ;
	private String             ACCU_USER_ID3  ;
	private String             ACCU_USER_ID4  ;
	private String             CHARGING_ID    ;
	private String             FILE_NO        ;
	private String             FREE_CODE      ;
	private String             TPREMARK       ;
	private String             DEDUCT_REMARK2 ;
	private String             BASE_TPREMARK  ;
	private String             SOURCE_TYPE    ;
	private String             APNNI_GROUPID  ;
	private String             RECORDEXTENSION;
	private String             APNOI          ;
	private String             RA             ;
	private String             VISIT_COUNTY   ;
	private String             BUSI_DOMAIN    ;
	private String             CHAN_NO        ;
	private String             RATE_FILE_NAME ;
	private String             ACC_FILE_NAME  ;
	private String             DIS_FILE_NAME  ;
	private String             FILE_PP_NAME   ;
	private String             DEDUCT_REMARK  ;
	private String             LAC            ;
	private String             CELL_ID        ;
	private String             BILLING_CYCLE  ;
	private String             APNNI          ;
	private String             ERROR_CODE     ;
	private String             BILLINFO       ;
	public char getRECORD_TYPE() {
		return RECORD_TYPE;
	}
	public void setRECORD_TYPE(char rECORD_TYPE) {
		RECORD_TYPE = rECORD_TYPE;
	}
	public char getNI_PDP() {
		return NI_PDP;
	}
	public void setNI_PDP(char nI_PDP) {
		NI_PDP = nI_PDP;
	}
	public char getMSNC() {
		return MSNC;
	}
	public void setMSNC(char mSNC) {
		MSNC = mSNC;
	}
	public char getPDP_TYPE() {
		return PDP_TYPE;
	}
	public void setPDP_TYPE(char pDP_TYPE) {
		PDP_TYPE = pDP_TYPE;
	}
	public Character getSGSN_CHANGE() {
		return SGSN_CHANGE;
	}
	public void setSGSN_CHANGE(Character sGSN_CHANGE) {
		SGSN_CHANGE = sGSN_CHANGE;
	}
	public String getCAUSE_CLOSE() {
		return CAUSE_CLOSE;
	}
	public void setCAUSE_CLOSE(String cAUSE_CLOSE) {
		CAUSE_CLOSE = cAUSE_CLOSE;
	}
	public char getRESULT() {
		return RESULT;
	}
	public void setRESULT(char rESULT) {
		RESULT = rESULT;
	}
	public char getROAM_TYPE() {
		return ROAM_TYPE;
	}
	public void setROAM_TYPE(char rOAM_TYPE) {
		ROAM_TYPE = rOAM_TYPE;
	}
	public char getTARIFF1() {
		return TARIFF1;
	}
	public void setTARIFF1(char tARIFF1) {
		TARIFF1 = tARIFF1;
	}
	public char getTARIFF2() {
		return TARIFF2;
	}
	public void setTARIFF2(char tARIFF2) {
		TARIFF2 = tARIFF2;
	}
	public char getTARIFF3() {
		return TARIFF3;
	}
	public void setTARIFF3(char tARIFF3) {
		TARIFF3 = tARIFF3;
	}
	public char getTARIFF4() {
		return TARIFF4;
	}
	public void setTARIFF4(char tARIFF4) {
		TARIFF4 = tARIFF4;
	}
	public char getTARIFF5() {
		return TARIFF5;
	}
	public void setTARIFF5(char tARIFF5) {
		TARIFF5 = tARIFF5;
	}
	public char getTARIFF6() {
		return TARIFF6;
	}
	public void setTARIFF6(char tARIFF6) {
		TARIFF6 = tARIFF6;
	}
	public char getRATEMETHOD() {
		return RATEMETHOD;
	}
	public void setRATEMETHOD(char rATEMETHOD) {
		RATEMETHOD = rATEMETHOD;
	}
	public String getDEAL_TIME() {
		return DEAL_TIME;
	}
	public void setDEAL_TIME(String dEAL_TIME) {
		DEAL_TIME = dEAL_TIME;
	}
	
	public String getUSER_TYPE() {
		return USER_TYPE;
	}
	public void setUSER_TYPE(String uSER_TYPE) {
		USER_TYPE = uSER_TYPE;
	}
	public String getFEE_TYPE() {
		return FEE_TYPE;
	}
	public void setFEE_TYPE(String fEE_TYPE) {
		FEE_TYPE = fEE_TYPE;
	}
	public String getA_SERV_TYPE() {
		return A_SERV_TYPE;
	}
	public void setA_SERV_TYPE(String a_SERV_TYPE) {
		A_SERV_TYPE = a_SERV_TYPE;
	}
	public String getSERVICE_TYPE() {
		return SERVICE_TYPE;
	}
	public void setSERVICE_TYPE(String sERVICE_TYPE) {
		SERVICE_TYPE = sERVICE_TYPE;
	}
	public String getOFFICE_CODE() {
		return OFFICE_CODE;
	}
	public void setOFFICE_CODE(String oFFICE_CODE) {
		OFFICE_CODE = oFFICE_CODE;
	}
	public String getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	public String getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(String sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	public Short getPAY_MODE() {
		return PAY_MODE;
	}
	public void setPAY_MODE(Short pAY_MODE) {
		PAY_MODE = pAY_MODE;
	}
	public Long getDATA_UP1() {
		return DATA_UP1;
	}
	public void setDATA_UP1(Long dATA_UP1) {
		DATA_UP1 = dATA_UP1;
	}
	public Long getDATA_DOWN1() {
		return DATA_DOWN1;
	}
	public void setDATA_DOWN1(Long dATA_DOWN1) {
		DATA_DOWN1 = dATA_DOWN1;
	}
	public Long getDATA_UP2() {
		return DATA_UP2;
	}
	public void setDATA_UP2(Long dATA_UP2) {
		DATA_UP2 = dATA_UP2;
	}
	public Long getDATA_DOWN2() {
		return DATA_DOWN2;
	}
	public void setDATA_DOWN2(Long dATA_DOWN2) {
		DATA_DOWN2 = dATA_DOWN2;
	}
	public Long getDATA_UP3() {
		return DATA_UP3;
	}
	public void setDATA_UP3(Long dATA_UP3) {
		DATA_UP3 = dATA_UP3;
	}
	public Long getDATA_DOWN3() {
		return DATA_DOWN3;
	}
	public void setDATA_DOWN3(Long dATA_DOWN3) {
		DATA_DOWN3 = dATA_DOWN3;
	}
	public Long getDATA_UP4() {
		return DATA_UP4;
	}
	public void setDATA_UP4(Long dATA_UP4) {
		DATA_UP4 = dATA_UP4;
	}
	public Long getDATA_DOWN4() {
		return DATA_DOWN4;
	}
	public void setDATA_DOWN4(Long dATA_DOWN4) {
		DATA_DOWN4 = dATA_DOWN4;
	}
	public Long getDATA_UP5() {
		return DATA_UP5;
	}
	public void setDATA_UP5(Long dATA_UP5) {
		DATA_UP5 = dATA_UP5;
	}
	public Long getDATA_DOWN5() {
		return DATA_DOWN5;
	}
	public void setDATA_DOWN5(Long dATA_DOWN5) {
		DATA_DOWN5 = dATA_DOWN5;
	}
	public Long getDATA_UP6() {
		return DATA_UP6;
	}
	public void setDATA_UP6(Long dATA_UP6) {
		DATA_UP6 = dATA_UP6;
	}
	public Long getDATA_DOWN6() {
		return DATA_DOWN6;
	}
	public void setDATA_DOWN6(Long dATA_DOWN6) {
		DATA_DOWN6 = dATA_DOWN6;
	}
	public Long getCFEE() {
		return CFEE;
	}
	public void setCFEE(Long cFEE) {
		CFEE = cFEE;
	}
	public Long getFEE1() {
		return FEE1;
	}
	public void setFEE1(Long fEE1) {
		FEE1 = fEE1;
	}
	public Long getFEE2() {
		return FEE2;
	}
	public void setFEE2(Long fEE2) {
		FEE2 = fEE2;
	}
	public Long getFEE3() {
		return FEE3;
	}
	public void setFEE3(Long fEE3) {
		FEE3 = fEE3;
	}
	public Long getTOTAL_FEE() {
		return TOTAL_FEE;
	}
	public void setTOTAL_FEE(Long tOTAL_FEE) {
		TOTAL_FEE = tOTAL_FEE;
	}
	public Long getFREE1() {
		return FREE1;
	}
	public void setFREE1(Long fREE1) {
		FREE1 = fREE1;
	}
	public Long getFREE2() {
		return FREE2;
	}
	public void setFREE2(Long fREE2) {
		FREE2 = fREE2;
	}
	public Long getFREE3() {
		return FREE3;
	}
	public void setFREE3(Long fREE3) {
		FREE3 = fREE3;
	}
	public Long getFREE4() {
		return FREE4;
	}
	public void setFREE4(Long fREE4) {
		FREE4 = fREE4;
	}
	public Long getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(Long uSER_ID) {
		USER_ID = uSER_ID;
	}
	public short getREVISION() {
		return REVISION;
	}
	public void setREVISION(short rEVISION) {
		REVISION = rEVISION;
	}
	public Long getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(Long fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	public int getA_BRAND_ID() {
		return A_BRAND_ID;
	}
	public void setA_BRAND_ID(int a_BRAND_ID) {
		A_BRAND_ID = a_BRAND_ID;
	}
	public int getCHANNEL_NO() {
		return CHANNEL_NO;
	}
	public void setCHANNEL_NO(int cHANNEL_NO) {
		CHANNEL_NO = cHANNEL_NO;
	}
	public int getFREE_ITEM1() {
		return FREE_ITEM1;
	}
	public void setFREE_ITEM1(int fREE_ITEM1) {
		FREE_ITEM1 = fREE_ITEM1;
	}
	public int getFREE_ITEM2() {
		return FREE_ITEM2;
	}
	public void setFREE_ITEM2(int fREE_ITEM2) {
		FREE_ITEM2 = fREE_ITEM2;
	}
	public int getFREE_ITEM3() {
		return FREE_ITEM3;
	}
	public void setFREE_ITEM3(int fREE_ITEM3) {
		FREE_ITEM3 = fREE_ITEM3;
	}
	public int getFREE_ITEM4() {
		return FREE_ITEM4;
	}
	public void setFREE_ITEM4(int fREE_ITEM4) {
		FREE_ITEM4 = fREE_ITEM4;
	}
	public int getCALL_DURATION() {
		return CALL_DURATION;
	}
	public void setCALL_DURATION(int cALL_DURATION) {
		CALL_DURATION = cALL_DURATION;
	}
	public int getDURATION1() {
		return DURATION1;
	}
	public void setDURATION1(int dURATION1) {
		DURATION1 = dURATION1;
	}
	public int getDURATION2() {
		return DURATION2;
	}
	public void setDURATION2(int dURATION2) {
		DURATION2 = dURATION2;
	}
	public int getDURATION3() {
		return DURATION3;
	}
	public void setDURATION3(int dURATION3) {
		DURATION3 = dURATION3;
	}
	public int getDURATION4() {
		return DURATION4;
	}
	public void setDURATION4(int dURATION4) {
		DURATION4 = dURATION4;
	}
	public int getDURATION5() {
		return DURATION5;
	}
	public void setDURATION5(int dURATION5) {
		DURATION5 = dURATION5;
	}
	public int getDURATION6() {
		return DURATION6;
	}
	public void setDURATION6(int dURATION6) {
		DURATION6 = dURATION6;
	}
	public int getRCDSEQNUM() {
		return RCDSEQNUM;
	}
	public void setRCDSEQNUM(int rCDSEQNUM) {
		RCDSEQNUM = rCDSEQNUM;
	}
	public int getFREE_FEE1() {
		return FREE_FEE1;
	}
	public void setFREE_FEE1(int fREE_FEE1) {
		FREE_FEE1 = fREE_FEE1;
	}
	public int getFREE_FEE2() {
		return FREE_FEE2;
	}
	public void setFREE_FEE2(int fREE_FEE2) {
		FREE_FEE2 = fREE_FEE2;
	}
	public int getFREE_FEE3() {
		return FREE_FEE3;
	}
	public void setFREE_FEE3(int fREE_FEE3) {
		FREE_FEE3 = fREE_FEE3;
	}
	public int getFREE_FEE4() {
		return FREE_FEE4;
	}
	public void setFREE_FEE4(int fREE_FEE4) {
		FREE_FEE4 = fREE_FEE4;
	}
	public int getBILL_START_DAY() {
		return BILL_START_DAY;
	}
	public void setBILL_START_DAY(int bILL_START_DAY) {
		BILL_START_DAY = bILL_START_DAY;
	}
	public int getBILL_END_DAY() {
		return BILL_END_DAY;
	}
	public void setBILL_END_DAY(int bILL_END_DAY) {
		BILL_END_DAY = bILL_END_DAY;
	}
	public int getCDR_DAY() {
		return CDR_DAY;
	}
	public void setCDR_DAY(int cDR_DAY) {
		CDR_DAY = cDR_DAY;
	}
	public String getHOME_AREA_CODE() {
		return HOME_AREA_CODE;
	}
	public void setHOME_AREA_CODE(String hOME_AREA_CODE) {
		HOME_AREA_CODE = hOME_AREA_CODE;
	}
	public String getVISIT_AREA_CODE() {
		return VISIT_AREA_CODE;
	}
	public void setVISIT_AREA_CODE(String vISIT_AREA_CODE) {
		VISIT_AREA_CODE = vISIT_AREA_CODE;
	}
	public String getFREE_CODE1() {
		return FREE_CODE1;
	}
	public void setFREE_CODE1(String fREE_CODE1) {
		FREE_CODE1 = fREE_CODE1;
	}
	public String getFREE_CODE2() {
		return FREE_CODE2;
	}
	public void setFREE_CODE2(String fREE_CODE2) {
		FREE_CODE2 = fREE_CODE2;
	}
	public String getFREE_CODE3() {
		return FREE_CODE3;
	}
	public void setFREE_CODE3(String fREE_CODE3) {
		FREE_CODE3 = fREE_CODE3;
	}
	public String getFREE_CODE4() {
		return FREE_CODE4;
	}
	public void setFREE_CODE4(String fREE_CODE4) {
		FREE_CODE4 = fREE_CODE4;
	}
	public String getPRODUCT_ID1() {
		return PRODUCT_ID1;
	}
	public void setPRODUCT_ID1(String pRODUCT_ID1) {
		PRODUCT_ID1 = pRODUCT_ID1;
	}
	public String getINSTANCE_ID1() {
		return INSTANCE_ID1;
	}
	public void setINSTANCE_ID1(String iNSTANCE_ID1) {
		INSTANCE_ID1 = iNSTANCE_ID1;
	}
	public String getPRODUCT_ID2() {
		return PRODUCT_ID2;
	}
	public void setPRODUCT_ID2(String pRODUCT_ID2) {
		PRODUCT_ID2 = pRODUCT_ID2;
	}
	public String getINSTANCE_ID2() {
		return INSTANCE_ID2;
	}
	public void setINSTANCE_ID2(String iNSTANCE_ID2) {
		INSTANCE_ID2 = iNSTANCE_ID2;
	}
	public String getPRODUCT_ID3() {
		return PRODUCT_ID3;
	}
	public void setPRODUCT_ID3(String pRODUCT_ID3) {
		PRODUCT_ID3 = pRODUCT_ID3;
	}
	public String getINSTANCE_ID3() {
		return INSTANCE_ID3;
	}
	public void setINSTANCE_ID3(String iNSTANCE_ID3) {
		INSTANCE_ID3 = iNSTANCE_ID3;
	}
	public String getPRODUCT_ID4() {
		return PRODUCT_ID4;
	}
	public void setPRODUCT_ID4(String pRODUCT_ID4) {
		PRODUCT_ID4 = pRODUCT_ID4;
	}
	public String getINSTANCE_ID4() {
		return INSTANCE_ID4;
	}
	public void setINSTANCE_ID4(String iNSTANCE_ID4) {
		INSTANCE_ID4 = iNSTANCE_ID4;
	}
	public String getRATE_ITEM_ID1() {
		return RATE_ITEM_ID1;
	}
	public void setRATE_ITEM_ID1(String rATE_ITEM_ID1) {
		RATE_ITEM_ID1 = rATE_ITEM_ID1;
	}
	public String getRATE_ITEM_ID2() {
		return RATE_ITEM_ID2;
	}
	public void setRATE_ITEM_ID2(String rATE_ITEM_ID2) {
		RATE_ITEM_ID2 = rATE_ITEM_ID2;
	}
	public String getRATE_ITEM_ID3() {
		return RATE_ITEM_ID3;
	}
	public void setRATE_ITEM_ID3(String rATE_ITEM_ID3) {
		RATE_ITEM_ID3 = rATE_ITEM_ID3;
	}
	public String getRATE_ITEM_ID4() {
		return RATE_ITEM_ID4;
	}
	public void setRATE_ITEM_ID4(String rATE_ITEM_ID4) {
		RATE_ITEM_ID4 = rATE_ITEM_ID4;
	}
	public String getRATE_TIME() {
		return RATE_TIME;
	}
	public void setRATE_TIME(String rATE_TIME) {
		RATE_TIME = rATE_TIME;
	}
	public String getACC_TIME() {
		return ACC_TIME;
	}
	public void setACC_TIME(String aCC_TIME) {
		ACC_TIME = aCC_TIME;
	}
	public String getDIS_TIME() {
		return DIS_TIME;
	}
	public void setDIS_TIME(String dIS_TIME) {
		DIS_TIME = dIS_TIME;
	}
	public String getMSISDN() {
		return MSISDN;
	}
	public void setMSISDN(String mSISDN) {
		MSISDN = mSISDN;
	}
	public String getIMSI_NUMBER() {
		return IMSI_NUMBER;
	}
	public void setIMSI_NUMBER(String iMSI_NUMBER) {
		IMSI_NUMBER = iMSI_NUMBER;
	}
	public String getSGSN() {
		return SGSN;
	}
	public void setSGSN(String sGSN) {
		SGSN = sGSN;
	}
	public String getGGSN() {
		return GGSN;
	}
	public void setGGSN(String gGSN) {
		GGSN = gGSN;
	}
	public String getSPA() {
		return SPA;
	}
	public void setSPA(String sPA) {
		SPA = sPA;
	}
	public String getCUST_ID() {
		return CUST_ID;
	}
	public void setCUST_ID(String cUST_ID) {
		CUST_ID = cUST_ID;
	}
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public String getACCU_USER_ID1() {
		return ACCU_USER_ID1;
	}
	public void setACCU_USER_ID1(String aCCU_USER_ID1) {
		ACCU_USER_ID1 = aCCU_USER_ID1;
	}
	public String getACCU_USER_ID2() {
		return ACCU_USER_ID2;
	}
	public void setACCU_USER_ID2(String aCCU_USER_ID2) {
		ACCU_USER_ID2 = aCCU_USER_ID2;
	}
	public String getACCU_USER_ID3() {
		return ACCU_USER_ID3;
	}
	public void setACCU_USER_ID3(String aCCU_USER_ID3) {
		ACCU_USER_ID3 = aCCU_USER_ID3;
	}
	public String getACCU_USER_ID4() {
		return ACCU_USER_ID4;
	}
	public void setACCU_USER_ID4(String aCCU_USER_ID4) {
		ACCU_USER_ID4 = aCCU_USER_ID4;
	}
	public String getCHARGING_ID() {
		return CHARGING_ID;
	}
	public void setCHARGING_ID(String cHARGING_ID) {
		CHARGING_ID = cHARGING_ID;
	}
	public String getFILE_NO() {
		return FILE_NO;
	}
	public void setFILE_NO(String fILE_NO) {
		FILE_NO = fILE_NO;
	}
	public String getFREE_CODE() {
		return FREE_CODE;
	}
	public void setFREE_CODE(String fREE_CODE) {
		FREE_CODE = fREE_CODE;
	}
	public String getTPREMARK() {
		return TPREMARK;
	}
	public void setTPREMARK(String tPREMARK) {
		TPREMARK = tPREMARK;
	}
	public String getDEDUCT_REMARK2() {
		return DEDUCT_REMARK2;
	}
	public void setDEDUCT_REMARK2(String dEDUCT_REMARK2) {
		DEDUCT_REMARK2 = dEDUCT_REMARK2;
	}
	public String getBASE_TPREMARK() {
		return BASE_TPREMARK;
	}
	public void setBASE_TPREMARK(String bASE_TPREMARK) {
		BASE_TPREMARK = bASE_TPREMARK;
	}
	public String getSOURCE_TYPE() {
		return SOURCE_TYPE;
	}
	public void setSOURCE_TYPE(String sOURCE_TYPE) {
		SOURCE_TYPE = sOURCE_TYPE;
	}
	public String getAPNNI_GROUPID() {
		return APNNI_GROUPID;
	}
	public void setAPNNI_GROUPID(String aPNNI_GROUPID) {
		APNNI_GROUPID = aPNNI_GROUPID;
	}
	public String getRECORDEXTENSION() {
		return RECORDEXTENSION;
	}
	public void setRECORDEXTENSION(String rECORDEXTENSION) {
		RECORDEXTENSION = rECORDEXTENSION;
	}
	public String getAPNOI() {
		return APNOI;
	}
	public void setAPNOI(String aPNOI) {
		APNOI = aPNOI;
	}
	public String getRA() {
		return RA;
	}
	public void setRA(String rA) {
		RA = rA;
	}
	public String getVISIT_COUNTY() {
		return VISIT_COUNTY;
	}
	public void setVISIT_COUNTY(String vISIT_COUNTY) {
		VISIT_COUNTY = vISIT_COUNTY;
	}
	public String getBUSI_DOMAIN() {
		return BUSI_DOMAIN;
	}
	public void setBUSI_DOMAIN(String bUSI_DOMAIN) {
		BUSI_DOMAIN = bUSI_DOMAIN;
	}
	public String getCHAN_NO() {
		return CHAN_NO;
	}
	public void setCHAN_NO(String cHAN_NO) {
		CHAN_NO = cHAN_NO;
	}
	public String getRATE_FILE_NAME() {
		return RATE_FILE_NAME;
	}
	public void setRATE_FILE_NAME(String rATE_FILE_NAME) {
		RATE_FILE_NAME = rATE_FILE_NAME;
	}
	public String getACC_FILE_NAME() {
		return ACC_FILE_NAME;
	}
	public void setACC_FILE_NAME(String aCC_FILE_NAME) {
		ACC_FILE_NAME = aCC_FILE_NAME;
	}
	public String getDIS_FILE_NAME() {
		return DIS_FILE_NAME;
	}
	public void setDIS_FILE_NAME(String dIS_FILE_NAME) {
		DIS_FILE_NAME = dIS_FILE_NAME;
	}
	public String getFILE_PP_NAME() {
		return FILE_PP_NAME;
	}
	public void setFILE_PP_NAME(String fILE_PP_NAME) {
		FILE_PP_NAME = fILE_PP_NAME;
	}
	public String getDEDUCT_REMARK() {
		return DEDUCT_REMARK;
	}
	public void setDEDUCT_REMARK(String dEDUCT_REMARK) {
		DEDUCT_REMARK = dEDUCT_REMARK;
	}
	public String getLAC() {
		return LAC;
	}
	public void setLAC(String lAC) {
		LAC = lAC;
	}
	public String getCELL_ID() {
		return CELL_ID;
	}
	public void setCELL_ID(String cELL_ID) {
		CELL_ID = cELL_ID;
	}
	public String getBILLING_CYCLE() {
		return BILLING_CYCLE;
	}
	public void setBILLING_CYCLE(String bILLING_CYCLE) {
		BILLING_CYCLE = bILLING_CYCLE;
	}
	public String getAPNNI() {
		return APNNI;
	}
	public void setAPNNI(String aPNNI) {
		APNNI = aPNNI;
	}
	public String getERROR_CODE() {
		return ERROR_CODE;
	}
	public void setERROR_CODE(String eRROR_CODE) {
		ERROR_CODE = eRROR_CODE;
	}
	public String getBILLINFO() {
		return BILLINFO;
	}
	public void setBILLINFO(String bILLINFO) {
		BILLINFO = bILLINFO;
	}
	
	

}