current_version=$(cat .versions/team-c-customer.version)
firsts_digit=$(echo "$current_version" | grep -oP '[0-9]+.[0-9]+.')
last_digit=$(echo "$current_version" | grep -oP '[0-9]+$')
new_version="$firsts_digit""$(($last_digit+1))"
echo "$new_version" > .versions/team-c-customer.version

git push
cd ../.versions
./update_version.sh
cd ../team-c-customer