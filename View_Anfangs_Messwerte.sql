CREATE OR REPLACE VIEW `anfangs_messwerte` AS
    SELECT 
        `messwerte`.`ZaehlerID` AS `ZaehlerID`,
        `messwerte`.`Ablesedatum` AS `Ablesung_von`,
        `messwerte`.`Messwert` AS `Messwert_von`,
        MIN(`messwerte_1`.`Ablesedatum`) AS `Ablesung_bis`
    FROM
        (`messwerte`
        LEFT JOIN `messwerte` `messwerte_1` ON ((`messwerte`.`ZaehlerID` = `messwerte_1`.`ZaehlerID`)))
    WHERE
        (`messwerte_1`.`Ablesedatum` > `messwerte`.`Ablesedatum`)
    GROUP BY `messwerte`.`ZaehlerID` , `messwerte`.`Ablesedatum` , `messwerte`.`Messwert`
    ORDER BY `messwerte`.`ZaehlerID` , `messwerte`.`Ablesedatum`