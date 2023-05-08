<?php
namespace App\Entity;

enum Niveau: string
{
    case Debutant = "Debutant";
    case Intermediaire = "Intermediaire";
    case Expert = "Expert";
}