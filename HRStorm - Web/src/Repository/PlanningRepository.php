<?php

namespace App\Repository;

use App\Entity\Planning;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Planning>
 *
 * @method Planning|null find($id, $lockMode = null, $lockVersion = null)
 * @method Planning|null findOneBy(array $criteria, array $orderBy = null)
 * @method Planning[]    findAll()
 * @method Planning[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class PlanningRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Planning::class);
    }

    public function save(Planning $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Planning $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }



    public function priorite()
    {
        return $this->createQueryBuilder('p')
            ->select('p.id', 'COUNT(t.id) as tache_count')
            ->from('App\Entity\Planning', 'p')
            ->leftJoin('p.taches', 't')
            ->groupBy('p.id')
            ->orderBy('tache_count', 'DESC')
            ->getQuery()
            ->getResult();

    }


    public function trie_croissant_datedeb()
    {
        return $this->createQueryBuilder('planning')
            ->orderBy('planning.dateDebut','ASC')
            ->getQuery()
            ->getResult();
    }

    public function trie_decroissant_datedeb()
    {
        return $this->createQueryBuilder('planning')
            ->orderBy('planning.dateDebut','DESC')
            ->getQuery()
            ->getResult();
    }

    public function trie_croissant_datefin()
    {
        return $this->createQueryBuilder('planning')
            ->orderBy('planning.dateFin','ASC')
            ->getQuery()
            ->getResult();
    }

    public function trie_decroissant_datefin()
    {
        return $this->createQueryBuilder('planning')
            ->orderBy('planning.dateFin','DESC')
            ->getQuery()
            ->getResult();
    }

//    /**
//     * @return Planning[] Returns an array of Planning objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('p.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Planning
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
