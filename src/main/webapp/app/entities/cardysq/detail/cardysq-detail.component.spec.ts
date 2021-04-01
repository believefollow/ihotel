import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CardysqDetailComponent } from './cardysq-detail.component';

describe('Component Tests', () => {
  describe('Cardysq Management Detail Component', () => {
    let comp: CardysqDetailComponent;
    let fixture: ComponentFixture<CardysqDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CardysqDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ cardysq: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CardysqDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CardysqDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cardysq on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cardysq).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
