package logic;

import java.util.Set;

// 使用状況（equipment_status）のチェック
public class EquipmentStatusValidator {

    // 許可コード
    private static final Set<String> VALID_STATUS = Set.of("1", "2", "3", "4");

    // 画面から送信された使用状況コード(equipment_status) の判定
    public static boolean isValid(String status) {
        
    	if (status == null) {
            return false;
        }
        
        return VALID_STATUS.contains(status);
        
    }
}