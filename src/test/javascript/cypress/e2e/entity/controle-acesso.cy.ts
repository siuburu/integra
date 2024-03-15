import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('ControleAcesso e2e test', () => {
  const controleAcessoPageUrl = '/controle-acesso';
  const controleAcessoPageUrlPattern = new RegExp('/controle-acesso(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const controleAcessoSample = {};

  let controleAcesso;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/controle-acessos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/controle-acessos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/controle-acessos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (controleAcesso) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/controle-acessos/${controleAcesso.id}`,
      }).then(() => {
        controleAcesso = undefined;
      });
    }
  });

  it('ControleAcessos menu should load ControleAcessos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('controle-acesso');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ControleAcesso').should('exist');
    cy.url().should('match', controleAcessoPageUrlPattern);
  });

  describe('ControleAcesso page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(controleAcessoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ControleAcesso page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/controle-acesso/new$'));
        cy.getEntityCreateUpdateHeading('ControleAcesso');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', controleAcessoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/controle-acessos',
          body: controleAcessoSample,
        }).then(({ body }) => {
          controleAcesso = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/controle-acessos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [controleAcesso],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(controleAcessoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ControleAcesso page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('controleAcesso');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', controleAcessoPageUrlPattern);
      });

      it('edit button click should load edit ControleAcesso page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ControleAcesso');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', controleAcessoPageUrlPattern);
      });

      it('edit button click should load edit ControleAcesso page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ControleAcesso');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', controleAcessoPageUrlPattern);
      });

      it('last delete button click should delete instance of ControleAcesso', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('controleAcesso').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', controleAcessoPageUrlPattern);

        controleAcesso = undefined;
      });
    });
  });

  describe('new ControleAcesso page', () => {
    beforeEach(() => {
      cy.visit(`${controleAcessoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ControleAcesso');
    });

    it('should create an instance of ControleAcesso', () => {
      cy.get(`[data-cy="dataAcesso"]`).type('2024-03-10T15:43');
      cy.get(`[data-cy="dataAcesso"]`).blur();
      cy.get(`[data-cy="dataAcesso"]`).should('have.value', '2024-03-10T15:43');

      cy.get(`[data-cy="ipAcesso"]`).type('untrue deceivingly');
      cy.get(`[data-cy="ipAcesso"]`).should('have.value', 'untrue deceivingly');

      cy.get(`[data-cy="nomeDispositivo"]`).type('midst inasmuch innocently');
      cy.get(`[data-cy="nomeDispositivo"]`).should('have.value', 'midst inasmuch innocently');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        controleAcesso = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', controleAcessoPageUrlPattern);
    });
  });
});
