<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;
use App\Repository\SalaireRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: SalaireRepository::class)]
class Salaire
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column]
    /**
     * @Assert\NotBlank(message="l'id doit etre non vide")
     * @Groups("post:read")
     */
    private ?int $id_user = null;

    #[ORM\Column]
    /**
     * @Assert\NotNull(message="le montant doit etre non nul")
     * @Groups("post:read")
     */
    private ?float $montant = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #  /**
        #  * @Assert\Date
        #  * @var date A "Y-m-d" formatted value
        #  */
    private ?\DateTimeInterface $date = null;

    #[ORM\Column]
    /**
     * @Assert\NotBlank(message="le taux doit etre non vide")
     * @Groups("post:read")
     */
    private ?float $taux_augmentation = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdUser(): ?int
    {
        return $this->id_user;
    }

    public function setIdUser(int $id_user): self
    {
        $this->id_user = $id_user;

        return $this;
    }

    public function getMontant(): ?float
    {
        return $this->montant;
    }

    public function setMontant(float $montant): self
    {
        $this->montant = $montant;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getTauxAugmentation(): ?float
    {
        return $this->taux_augmentation;
    }

    public function setTauxAugmentation(float $taux_augmentation): self
    {
        $this->taux_augmentation = $taux_augmentation;

        return $this;
    }
}
