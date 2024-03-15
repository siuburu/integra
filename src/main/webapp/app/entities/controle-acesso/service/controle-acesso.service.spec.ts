import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IControleAcesso } from '../controle-acesso.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../controle-acesso.test-samples';

import { ControleAcessoService, RestControleAcesso } from './controle-acesso.service';

const requireRestSample: RestControleAcesso = {
  ...sampleWithRequiredData,
  dataAcesso: sampleWithRequiredData.dataAcesso?.toJSON(),
};

describe('ControleAcesso Service', () => {
  let service: ControleAcessoService;
  let httpMock: HttpTestingController;
  let expectedResult: IControleAcesso | IControleAcesso[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ControleAcessoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ControleAcesso', () => {
      const controleAcesso = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(controleAcesso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ControleAcesso', () => {
      const controleAcesso = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(controleAcesso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ControleAcesso', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ControleAcesso', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ControleAcesso', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addControleAcessoToCollectionIfMissing', () => {
      it('should add a ControleAcesso to an empty array', () => {
        const controleAcesso: IControleAcesso = sampleWithRequiredData;
        expectedResult = service.addControleAcessoToCollectionIfMissing([], controleAcesso);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controleAcesso);
      });

      it('should not add a ControleAcesso to an array that contains it', () => {
        const controleAcesso: IControleAcesso = sampleWithRequiredData;
        const controleAcessoCollection: IControleAcesso[] = [
          {
            ...controleAcesso,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addControleAcessoToCollectionIfMissing(controleAcessoCollection, controleAcesso);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ControleAcesso to an array that doesn't contain it", () => {
        const controleAcesso: IControleAcesso = sampleWithRequiredData;
        const controleAcessoCollection: IControleAcesso[] = [sampleWithPartialData];
        expectedResult = service.addControleAcessoToCollectionIfMissing(controleAcessoCollection, controleAcesso);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controleAcesso);
      });

      it('should add only unique ControleAcesso to an array', () => {
        const controleAcessoArray: IControleAcesso[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const controleAcessoCollection: IControleAcesso[] = [sampleWithRequiredData];
        expectedResult = service.addControleAcessoToCollectionIfMissing(controleAcessoCollection, ...controleAcessoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const controleAcesso: IControleAcesso = sampleWithRequiredData;
        const controleAcesso2: IControleAcesso = sampleWithPartialData;
        expectedResult = service.addControleAcessoToCollectionIfMissing([], controleAcesso, controleAcesso2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controleAcesso);
        expect(expectedResult).toContain(controleAcesso2);
      });

      it('should accept null and undefined values', () => {
        const controleAcesso: IControleAcesso = sampleWithRequiredData;
        expectedResult = service.addControleAcessoToCollectionIfMissing([], null, controleAcesso, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controleAcesso);
      });

      it('should return initial array if no ControleAcesso is added', () => {
        const controleAcessoCollection: IControleAcesso[] = [sampleWithRequiredData];
        expectedResult = service.addControleAcessoToCollectionIfMissing(controleAcessoCollection, undefined, null);
        expect(expectedResult).toEqual(controleAcessoCollection);
      });
    });

    describe('compareControleAcesso', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareControleAcesso(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareControleAcesso(entity1, entity2);
        const compareResult2 = service.compareControleAcesso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareControleAcesso(entity1, entity2);
        const compareResult2 = service.compareControleAcesso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareControleAcesso(entity1, entity2);
        const compareResult2 = service.compareControleAcesso(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
