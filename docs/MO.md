# Monetization Spec - Money Tracker

## App Structure

| Screen | Type | Description |
|--------|------|-------------|
| SplashActivity | Launcher | App startup with logo, auto navigate |
| MainActivity | Container | Bottom navigation with 3 tabs |
| HomeFragment | Main | Transactions list, summary stats |
| StatisticsFragment | Tab | Charts, reports |
| SettingsFragment | Tab | Settings with native ad |
| AddTransactionActivity | Child | Add new transaction |

## Ad Placements

| nameSpace | Màn hình / Trigger | Loại | interval | stepCount | Status |
|---|---|---|---|---|---|
| nsp_ao_splash | App khởi động (Splash) | App Open | - | - | ✅ Implemented |
| nsp_ao_resume | Quay lại từ background | App Open Resume | - | - | ✅ Auto by SDK |
| nsp_inter_main | Tab switching in MainActivity | Interstitial | 25 | 1 | ✅ Implemented |
| nsp_inter_add_transaction | Save new transaction | Interstitial | 25 | 1 | ✅ Implemented |
| nsp_bn_home_bottom | HomeFragment - bottom | Banner | - | - | ✅ Implemented |
| nsp_native_settings | SettingsFragment | Native | - | - | ✅ Implemented |

## Notes
- App Open Resume (nsp_ao_resume) tự động bởi SDK khi `resumeAds` được cấu hình
- Banner ở Home sử dụng banner_adaptive
- Native ở Settings sử dụng native_medium
- Interstitial hiển thị sau khi save transaction thành công
