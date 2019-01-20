DELETE
from decision_table;

INSERT INTO decision_table (machine_id, from_level, to_level, alert_action, recipients)
VALUES (1, null, 'LOW_DANGER', 'SMS', '8434259774,9199641284'),
       (1, 'LOW_WARNING', 'LOW_DANGER', 'SMS', '8434259774,9199641284'),
       (1, null, 'LOW_WARNING', 'SMS', '8434259774,9199641284');