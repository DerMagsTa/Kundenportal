CREATE OR REPLACE VIEW `verbrauch` AS
    SELECT 
		`zaehler`.`ID` AS `ZaehlerID`,
        `zaehler`.`Energieart` AS `Energieart`,
        `zaehler`.`ZaehlerNr` AS `ZaehlerNr`,
        `anfangs_messwerte`.`Ablesung_von` AS `Ablesung_von`,
        `anfangs_messwerte`.`Messwert_von` AS `Messwert_von`,
        `anfangs_messwerte`.`Ablesung_bis` AS `Ablesung_bis`,
        `messwerte`.`Messwert` AS `Messwert_bis`,
        (`messwerte`.`Messwert` - `anfangs_messwerte`.`Messwert_von`) AS `Verbrauch`
    FROM
        ((`anfangs_messwerte`
        LEFT JOIN `messwerte` ON (((`anfangs_messwerte`.`ZaehlerID` = `messwerte`.`ZaehlerID`)
            AND (`anfangs_messwerte`.`Ablesung_bis` = `messwerte`.`Ablesedatum`))))
        LEFT JOIN `zaehler` ON ((`anfangs_messwerte`.`ZaehlerID` = `zaehler`.`ID`)))