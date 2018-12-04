package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels;
/*******************************************************/
/* Created by muhammad.sufwan on 4/25/2018 user email ${EMAIL};. */

/*******************************************************/
public class JobFormItemModel {

    private String ac_no,ac_location,sos_filters,sos_evaporator_coil,sos_blower_assambly,sos_condencer_coil,sos_fan_motor,sos_compressor,
    sos_line_sets_insulation,sos_unusual_vibration,sos_capacitor_electrical,rsp_type,sl_type,idu_ec_type,
            odu_cc_type,pic_type,ds_type,pcs_type,idu_odu_sys_type,general_comment;

    public JobFormItemModel(String ac_no, String ac_location, String sos_filters, String sos_evaporator_coil, String sos_blower_assambly,
                            String sos_condencer_coil, String sos_fan_motor, String sos_compressor, String sos_line_sets_insulation,
                            String sos_unusual_vibration, String sos_capacitor_electrical, String rsp_type, String sl_type,
                            String idu_ec_type, String odu_cc_type, String pic_type, String ds_type, String pcs_type,
                            String idu_odu_sys_type, String general_comment) {
        this.ac_no = ac_no;
        this.ac_location = ac_location;
        this.sos_filters = sos_filters;
        this.sos_evaporator_coil = sos_evaporator_coil;
        this.sos_blower_assambly = sos_blower_assambly;
        this.sos_condencer_coil = sos_condencer_coil;
        this.sos_fan_motor = sos_fan_motor;
        this.sos_compressor = sos_compressor;
        this.sos_line_sets_insulation = sos_line_sets_insulation;
        this.sos_unusual_vibration = sos_unusual_vibration;
        this.sos_capacitor_electrical = sos_capacitor_electrical;
        this.rsp_type = rsp_type;
        this.sl_type = sl_type;
        this.idu_ec_type = idu_ec_type;
        this.odu_cc_type = odu_cc_type;
        this.pic_type = pic_type;
        this.ds_type = ds_type;
        this.pcs_type = pcs_type;
        this.idu_odu_sys_type = idu_odu_sys_type;
        this.general_comment = general_comment;
    }

    public String getAc_no() {
        return ac_no;
    }

    public String getAc_location() {
        return ac_location;
    }

    public String getSos_filters() {
        return sos_filters;
    }

    public String getSos_evaporator_coil() {
        return sos_evaporator_coil;
    }

    public String getSos_blower_assambly() {
        return sos_blower_assambly;
    }

    public String getSos_condencer_coil() {
        return sos_condencer_coil;
    }

    public String getSos_fan_motor() {
        return sos_fan_motor;
    }

    public String getSos_compressor() {
        return sos_compressor;
    }

    public String getSos_line_sets_insulation() {
        return sos_line_sets_insulation;
    }

    public String getSos_unusual_vibration() {
        return sos_unusual_vibration;
    }

    public String getSos_capacitor_electrical() {
        return sos_capacitor_electrical;
    }

    public String getRsp_type() {
        return rsp_type;
    }

    public String getSl_type() {
        return sl_type;
    }

    public String getIdu_ec_type() {
        return idu_ec_type;
    }

    public String getOdu_cc_type() {
        return odu_cc_type;
    }

    public String getPic_type() {
        return pic_type;
    }

    public String getDs_type() {
        return ds_type;
    }

    public String getPcs_type() {
        return pcs_type;
    }

    public String getIdu_odu_sys_type() {
        return idu_odu_sys_type;
    }

    public String getGeneral_comment() {
        return general_comment;
    }
}
