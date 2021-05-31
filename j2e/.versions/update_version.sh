# get old castexski version number
old_content=$(cat castexski.version)
last_line=$(echo "$old_content" | grep -oP '(^castexski\/j2e.*:[[:space:]](([0-9]+\.){2}([0-9]))){1}')
firsts_digit_version=$(echo "$last_line" | grep -oP '[0-9]+.[0-9]+.')
last_digit_version=$(echo "$last_line" | grep -oP '[0-9]+$')

# add 1 to last digit to improve version
new_digit_version=$(($last_digit_version+1))

# append to the beginning of the version (format ab.cd.xy)
new_version="$firsts_digit_version$new_digit_version"

echo "Versions of submodules: \n" > castexski.version
for file in team-c-*.version
do
  name_file=$file
  content_file=$(cat $file)
  # append version of each file to castexski.version
  printf '%-28s: %s\n' "$name_file" "$content_file" >> castexski.version
done
printf "\nVersion of castexski/j2e project: \n" >> castexski.version
# append new version of castexski
printf '%-28s: %s\n' "castexski/j2e" $new_version>> castexski.version

# update castexski  branch with changes
cd ..
./pull_modules.sh
cd .versions
git add $(ls *.version)
git commit -m "module(s) upgraded"
git push