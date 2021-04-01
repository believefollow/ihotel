jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FkCzService } from '../service/fk-cz.service';
import { IFkCz, FkCz } from '../fk-cz.model';

import { FkCzUpdateComponent } from './fk-cz-update.component';

describe('Component Tests', () => {
  describe('FkCz Management Update Component', () => {
    let comp: FkCzUpdateComponent;
    let fixture: ComponentFixture<FkCzUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let fkCzService: FkCzService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FkCzUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FkCzUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FkCzUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      fkCzService = TestBed.inject(FkCzService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const fkCz: IFkCz = { id: 456 };

        activatedRoute.data = of({ fkCz });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(fkCz));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fkCz = { id: 123 };
        spyOn(fkCzService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fkCz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fkCz }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(fkCzService.update).toHaveBeenCalledWith(fkCz);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fkCz = new FkCz();
        spyOn(fkCzService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fkCz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fkCz }));
        saveSubject.complete();

        // THEN
        expect(fkCzService.create).toHaveBeenCalledWith(fkCz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fkCz = { id: 123 };
        spyOn(fkCzService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fkCz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(fkCzService.update).toHaveBeenCalledWith(fkCz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
