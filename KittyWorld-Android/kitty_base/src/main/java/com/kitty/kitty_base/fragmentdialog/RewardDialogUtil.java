package com.kitty.kitty_base.fragmentdialog;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.kitty.kitty_base.constant.IntentConfig;
import com.kitty.kitty_base.base.BaseDialogFragment;
import com.kitty.kitty_base.base.RewardObject;
import com.kitty.kitty_base.model.TaskLevelModel;

public class RewardDialogUtil {

    public static void showRewardDialog(RewardObject rewardObject, FragmentManager fragmentManager) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.LUCK_WHEEL_MODEL, rewardObject);
        if (rewardObject.rewardType == 3) {
            TimeBenifitDialog timeBenifitDialog = new TimeBenifitDialog();
            timeBenifitDialog.setArguments(bundle);
            timeBenifitDialog.show(fragmentManager, "timeBenifitDialog");
        } else if (rewardObject.rewardType == 5) {
            TimeKittyDialog timeKittyDialog = new TimeKittyDialog();
            timeKittyDialog.setArguments(bundle);
            timeKittyDialog.show(fragmentManager, "timeKittyDialog");
        } else if (rewardObject.rewardType == 6) {
            TreasureBoxDialog treasureBoxDialog = new TreasureBoxDialog();
            treasureBoxDialog.setArguments(bundle);
            treasureBoxDialog.show(fragmentManager, "treasureBoxDialog");
        } else if (rewardObject.rewardType == 9) { // 自定义type
            AdsTimeBenifitDialog adsTimeBenifitDialog = new AdsTimeBenifitDialog();
            adsTimeBenifitDialog.setArguments(bundle);
            adsTimeBenifitDialog.show(fragmentManager, "adsTimeBenifitDialog");
        } else if (rewardObject.rewardType == 2) {
            BonusDialog bonusDialog = new BonusDialog();
            bonusDialog.setArguments(bundle);
            bonusDialog.show(fragmentManager, "bonusDialog");
        }
    }

    public static void showRewardDialog(RewardObject rewardObject, FragmentManager fragmentManager, BaseDialogFragment.IDismissListener iDismissListener) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.LUCK_WHEEL_MODEL, rewardObject);
        if (rewardObject.rewardType == 3) {
            TimeBenifitDialog timeBenifitDialog = new TimeBenifitDialog();
            timeBenifitDialog.setArguments(bundle);
            timeBenifitDialog.show(fragmentManager, "timeBenifitDialog");
            timeBenifitDialog.setOnDismissListener(iDismissListener);
        } else if (rewardObject.rewardType == 5) {
            TimeKittyDialog timeKittyDialog = new TimeKittyDialog();
            timeKittyDialog.setArguments(bundle);
            timeKittyDialog.show(fragmentManager, "timeKittyDialog");
            timeKittyDialog.setOnDismissListener(iDismissListener);
        } else if (rewardObject.rewardType == 6) {
            TreasureBoxDialog treasureBoxDialog = new TreasureBoxDialog();
            treasureBoxDialog.setArguments(bundle);
            treasureBoxDialog.show(fragmentManager, "treasureBoxDialog");
            treasureBoxDialog.setOnDismissListener(iDismissListener);
        } else if (rewardObject.rewardType == 1) {
            KittyDialog kittyDialog = new KittyDialog();
            kittyDialog.setArguments(bundle);
            kittyDialog.show(fragmentManager, "kittyDialog");
            kittyDialog.setOnDismissListener(iDismissListener);
        } else if (rewardObject.rewardType == 10) { // 自定义type
            TaskLevelDialog taskLevelDialog = new TaskLevelDialog();
            taskLevelDialog.setArguments(bundle);
            taskLevelDialog.show(fragmentManager, "taskLevelDialog");
            taskLevelDialog.setOnDismissListener(iDismissListener);
        } else if (rewardObject.rewardType == 2) {
            BonusDialog bonusDialog = new BonusDialog();
            bonusDialog.setArguments(bundle);
            bonusDialog.show(fragmentManager, "bonusDialog");
            bonusDialog.setOnDismissListener(iDismissListener);
        }
    }

    public static void showRewardDialog2(RewardObject rewardObject, FragmentManager fragmentManager, BaseDialogFragment.IDismissListener iDismissListener) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.LUCK_WHEEL_MODEL, rewardObject);
        if (rewardObject.rewardType == 3) {
            TimeBenifitDialog timeBenifitDialog = new TimeBenifitDialog();
            timeBenifitDialog.setArguments(bundle);
            timeBenifitDialog.show(fragmentManager, "timeBenifitDialog");
            timeBenifitDialog.setOnDismissListener(iDismissListener);
        } else if (rewardObject.rewardType == 5) {
            TimeKittyDialog timeKittyDialog = new TimeKittyDialog();
            timeKittyDialog.setArguments(bundle);
            timeKittyDialog.show(fragmentManager, "timeKittyDialog");
            timeKittyDialog.setOnDismissListener(iDismissListener);
        } else if (rewardObject.rewardType == 6) {
            TreasureBoxDialog treasureBoxDialog = new TreasureBoxDialog();
            treasureBoxDialog.setArguments(bundle);
            treasureBoxDialog.show(fragmentManager, "treasureBoxDialog");
            treasureBoxDialog.setOnDismissListener(iDismissListener);
        } else if (rewardObject.rewardType == 1) {
            KittyDialog kittyDialog = new KittyDialog();
            kittyDialog.setArguments(bundle);
            kittyDialog.show(fragmentManager, "kittyDialog");
            kittyDialog.setOnDismissListener(iDismissListener);
        } else if (rewardObject.rewardType == 10) { // 自定义type
            TaskLevelDialog taskLevelDialog = new TaskLevelDialog();
            taskLevelDialog.setArguments(bundle);
            taskLevelDialog.show(fragmentManager, "taskLevelDialog");
            taskLevelDialog.setOnDismissListener(iDismissListener);
        } else if (rewardObject.rewardType == 2) {
            TimeBenifitDialog2 bonusDialog = new TimeBenifitDialog2();
            bonusDialog.setArguments(bundle);
            bonusDialog.show(fragmentManager, "bonusDialog");
            bonusDialog.setOnDismissListener(iDismissListener);
        }
    }
}
