import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccountsDetailComponent } from './accounts-detail.component';

describe('Component Tests', () => {
  describe('Accounts Management Detail Component', () => {
    let comp: AccountsDetailComponent;
    let fixture: ComponentFixture<AccountsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AccountsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ accounts: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AccountsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accounts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accounts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
