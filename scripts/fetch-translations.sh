#!/bin/bash

declare -A android
android=([ar_SA]=ar [ca]=ca [cs]=cs [da]=da [de]=de [el]=el [es]=es [fa]=fa [fr]=fr [gl]=gl [hu]=hu [it]=it [ja]=ja [nl]=nl [nb_NO]=nb-rNO [pl]=pl [pt]=pt [pt_BR]=pt-rBR [ru]=ru [sl_SI]=sl-rSI [sr]=sr [tr_TR]=tr-rTR [uk]=uk [zh_CN]=zh-rCN [zh_TW]=zh-rTW)

BASE_DIR=`realpath -L $0 | xargs dirname`/../

for lang in ${!android[@]}
do
	echo Fetching translations for $lang
	target_app=$BASE_DIR/app/src/main/res/values-${android[$lang]}
	target_cert4android=$BASE_DIR/cert4android/src/main/res/values-${android[$lang]}

	mkdir -p $target_app
	curl -n "https://www.transifex.com/api/2/project/davx5/resource/app/translation/$lang?file" |
		sed 's/\.\.\./…/g' > $target_app/strings.xml

	#mkdir -p $target_cert4android
	#curl -n "https://www.transifex.com/api/2/project/davx5/resource/cert4android/translation/$lang?file" >$target_cert4android/strings.xml
done
