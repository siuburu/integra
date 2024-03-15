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

describe('Sistema e2e test', () => {
  const sistemaPageUrl = '/sistema';
  const sistemaPageUrlPattern = new RegExp('/sistema(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const sistemaSample = { group: 'true', nome: 'oh whack', descricao: 'panel follow' };

  let sistema;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/sistemas+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/sistemas').as('postEntityRequest');
    cy.intercept('DELETE', '/api/sistemas/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (sistema) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/sistemas/${sistema.id}`,
      }).then(() => {
        sistema = undefined;
      });
    }
  });

  it('Sistemas menu should load Sistemas page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('sistema');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Sistema').should('exist');
    cy.url().should('match', sistemaPageUrlPattern);
  });

  describe('Sistema page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(sistemaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Sistema page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/sistema/new$'));
        cy.getEntityCreateUpdateHeading('Sistema');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sistemaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/sistemas',
          body: sistemaSample,
        }).then(({ body }) => {
          sistema = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/sistemas+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [sistema],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(sistemaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Sistema page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('sistema');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sistemaPageUrlPattern);
      });

      it('edit button click should load edit Sistema page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Sistema');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sistemaPageUrlPattern);
      });

      it('edit button click should load edit Sistema page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Sistema');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sistemaPageUrlPattern);
      });

      it('last delete button click should delete instance of Sistema', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('sistema').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sistemaPageUrlPattern);

        sistema = undefined;
      });
    });
  });

  describe('new Sistema page', () => {
    beforeEach(() => {
      cy.visit(`${sistemaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Sistema');
    });

    it('should create an instance of Sistema', () => {
      cy.get(`[data-cy="group"]`).type('idolized');
      cy.get(`[data-cy="group"]`).should('have.value', 'idolized');

      cy.get(`[data-cy="nome"]`).type('communist ick sans');
      cy.get(`[data-cy="nome"]`).should('have.value', 'communist ick sans');

      cy.get(`[data-cy="descricao"]`).type('whileXXXXX');
      cy.get(`[data-cy="descricao"]`).should('have.value', 'whileXXXXX');

      cy.get(`[data-cy="homeUrl"]`).type('seemingly');
      cy.get(`[data-cy="homeUrl"]`).should('have.value', 'seemingly');

      cy.get(`[data-cy="logoUrl"]`).type('fairly');
      cy.get(`[data-cy="logoUrl"]`).should('have.value', 'fairly');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        sistema = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', sistemaPageUrlPattern);
    });
  });
});
