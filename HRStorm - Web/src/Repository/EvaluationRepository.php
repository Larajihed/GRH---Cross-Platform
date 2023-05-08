<?php

namespace App\Repository;

use App\Entity\Evaluation;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Evaluation>
 *
 * @method Evaluation|null find($id, $lockMode = null, $lockVersion = null)
 * @method Evaluation|null findOneBy(array $criteria, array $orderBy = null)
 * @method Evaluation[]    findAll()
 * @method Evaluation[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class EvaluationRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Evaluation::class);
    }

    public function save(Evaluation $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Evaluation $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function countByLevel(): array
    {
        $qb = $this->createQueryBuilder('e')
            ->select('e.Level as level, COUNT(e.id) as count')
            ->groupBy('level')
            ->orderBy('level');
    
        return $qb->getQuery()->getResult();
    }


    public function findEvaluation($searchTerm)
    {
        return $this->createQueryBuilder('e')
            ->leftJoin('e.Employee', 'emp')
            ->where('e.Date LIKE :searchTerm OR e.Commentaire LIKE :searchTerm OR emp.nom LIKE :searchTerm OR e.Experience LIKE :searchTerm OR e.Level LIKE :searchTerm')
            ->orWhere(':searchTerm MEMBER OF e.Competences')
           // ->orWhere('e.Poste.nom LIKE :searchTerm')
            ->setParameter('searchTerm', '%'.$searchTerm.'%')
            ->getQuery()
            ->getResult();
    }
    

    
    public function countByMonth(): array
{
    $conn = $this->getEntityManager()->getConnection();

    $sql = '
        SELECT YEAR(Date) AS year, MONTH(Date) AS month, COUNT(id) AS count
        FROM Evaluation
        GROUP BY year, month
        ORDER BY year, month
    ';
    $stmt = $conn->executeQuery($sql);

    return $stmt->fetchAll();
}

    
//    /**
//     * @return Evaluation[] Returns an array of Evaluation objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('e.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Evaluation
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
