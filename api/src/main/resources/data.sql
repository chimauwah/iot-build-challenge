DELETE
from decision_table;

INSERT INTO decision_table (machine_id, from_level, to_level, alert_action, recipients)
VALUES (1, null, 'LOW_DANGER', 'SMS', '+18434259774'),
       (1, 'LOW_WARNING', 'LOW_DANGER', 'SMS', '+18434259774'),
--        (1, null, 'LOW_WARNING', 'SMS', '+18434259774'),
       (1, null, 'LOW_WARNING', 'EMAIL', 'cuwah@captechconsulting.com');


-- add machine data
-- INSERT INTO public.machines (id, name, quantity, capacity, rate) VALUES (1, 'Armani', 5005, 10000, 135);
-- INSERT INTO public.machines (id, name, quantity, capacity, rate) VALUES (2, 'Polo', 0, 10000, 240);
-- INSERT INTO public.machines (id, name, quantity, capacity, rate) VALUES (3, 'Gucci', 8755, 10000, 415);
-- INSERT INTO public.machines (id, name, quantity, capacity, rate) VALUES (4, 'Fendi', 8793, 10000, 65);
