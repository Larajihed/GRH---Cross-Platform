<?php

namespace App\Repository;

use App\Entity\Budget;
use App\Entity\Depense;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Budget>
 *
 * @method Budget|null find($id, $lockMode = null, $lockVersion = null)
 * @method Budget|null findOneBy(array $criteria, array $orderBy = null)
 * @method Budget[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class BudgetRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Budget::class);
    }

    public function findAll()
    {
        return $this->createQueryBuilder('b')
            ->orderBy('b.date', 'ASC')
            ->getQuery()
            ->getResult()
            ;
    }

    public function save(Budget $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Budget $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
    public function getBudgetByDate(\DateTime $startDate, \DateTime $endDate)
    {
        return $this->createQueryBuilder('b')
            ->andWhere('b.date BETWEEN :startDate AND :endDate')
            ->setParameter('startDate', $startDate)
            ->setParameter('endDate', $endDate)
            ->getQuery()
            ->getResult();
    }

//    /**
//     * @return Budget[] Returns an array of Budget objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('b')
//            ->andWhere('b.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('b.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Budget
//    {
//        return $this->createQueryBuilder('b')
//            ->andWhere('b.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }

    public function getBudgetData()
    {
        $query = $this->createQueryBuilder('b')
            ->select('b.budget_materiel, b.budget_salaire, b.budget_service')
            ->getQuery();

        return $query->getResult();
    }

    public function findBudget($searchTerm)
    {
        return $this->createQueryBuilder('b')
            ->where('b.date LIKE :searchTerm OR b.prime LIKE :searchTerm OR b.budget LIKE :searchTerm ')
            ->setParameter('searchTerm', '%'.$searchTerm.'%')
            ->getQuery()
            ->getResult();
    }


    public function countByBudget(): array
    {
        $qb = $this->createQueryBuilder('b')
            ->select('b.budget as budget, COUNT(b.id) as count, SUM(b.budget_materiel + b.budget_salaire + b.budget_service) as total')
            ->groupBy('budget')
            ->orderBy('budget');

        return $qb->getQuery()->getResult();
    }
    public function countByMonth()
    {
        $conn = $this->getEntityManager()->getConnection();

        $sql = '
        SELECT YEAR(Date) AS year, MONTH(Date) AS month, COUNT(id) AS count, SUM(budget_materiel + budget_salaire + budget_service) AS total
        FROM Budget
        GROUP BY year, month
        ORDER BY year, month
    ';

        $stmt = $conn->executeQuery($sql);

        return $stmt->fetchAll();
    }


    public function countdByMonth()
    {
        $query = $this->createQueryBuilder('b')
            ->select("DATE_FORMAT(b.date, '%Y-%m') as month, SUM(b.budget_materiel + b.budget_salaire + b.budget_service) as total")
            ->groupBy('month')
            ->getQuery();

        return $query->getResult();
    }


    public function order_By_budget()
    {
        return $this->createQueryBuilder('b')
            ->orderBy('b.budget', 'ASC')
            ->getQuery()->getResult();
    }
    public function order_By_Dateb()
    {
        return $this->createQueryBuilder('b')
            ->orderBy('b.date', 'ASC')
            ->getQuery()->getResult();
    }


    public function searchdatenow()
    {
        $EM=$this->getEntityManager();
        $query = $EM->createQuery('select b from App\Entity\Budget b  WHERE b.date > CURRENT_DATE() ');
        //  ->setParameter('b', $date);
        return $query->getResult();
    }


}
