del "base_records.sql"
python3 csv_to_sql_insert.py "base_records.sql" "base/equipment.csv" "equipment"
python3 csv_to_sql_insert.py "base_records.sql" "base/performers.csv" "performer"
python3 csv_to_sql_insert.py "base_records.sql" "base/staff.csv" "staff"
python3 csv_to_sql_insert.py "base_records.sql" "base/performance_timeslot.csv" "performance_timeslot"
python3 csv_to_sql_insert.py "base_records.sql" "base/position_type.csv" "position_type"