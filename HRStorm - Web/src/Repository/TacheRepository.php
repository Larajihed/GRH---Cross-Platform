<?php

namespace App\Repository;

use App\Entity\Tache;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Tache>
 *
 * @method Tache|null find($id, $lockMode = null, $lockVersion = null)
 * @method Tache|null findOneBy(array $criteria, array $orderBy = null)
 * @method Tache[]    findAll()
 * @method Tache[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class TacheRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Tache::class);
    }

    public function save(Tache $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Tache $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function findTachebyname($name)
    {
        return $this->createQueryBuilder('tache')
            ->where('tache.nom LIKE :nom OR tache.description LIKE :nom OR tache.priorite LIKE :nom')
            ->setParameter('nom','%'.$name.'%')
            ->getQuery()
            ->getResult();
    }

    public function findByMedecinOrPatient($search,$med)
    {
        return $this->createQueryBuilder('rv')
            ->innerJoin(User::class, 'med', 'WITH', 'rv.med = med.id')
            ->innerJoin(User::class, 'patient', 'WITH', 'rv.patient = patient.id')
            ->where('med = :med')
            ->andWhere('med.nom LIKE :nom OR med.prenom LIKE :nom ')
            ->orWhere('patient.nom LIKE :nom OR patient.prenom LIKE :nom ')
            ->setParameter('nom','%'.$search.'%')
            ->setParameter('med',$med)
            ->orderBy('rv.date_rv', 'ASC')
            ->getQuery()
            ->getResult();
    }

//    /**
//     * @return Tache[] Returns an array of Tache objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('t')
//            ->andWhere('t.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('t.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Tache
//    {
//        return $this->createQueryBuilder('t')
//            ->andWhere('t.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
